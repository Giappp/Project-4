import { HttpClient } from '@angular/common/http';
import {
  APP_INITIALIZER,
  Inject,
  inject,
  Injectable,
  PLATFORM_ID,
  Provider,
} from '@angular/core';
import { Store } from '@ngrx/store';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { AuthState, AuthUser, TokenStatus } from '../store/auth.model';
import { AuthUserActions, RefreshTokenActions } from '../store/auth.action';

import * as AuthSelectors from '../store/auth.selectors';
import {
  filter,
  lastValueFrom,
  map,
  Observable,
  take,
  tap,
  throwError,
} from 'rxjs';
import { TokenStorageService } from '../../core/auth/token-storage.service';
import { isPlatformBrowser } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly store = inject(Store);
  private readonly http = inject(HttpClient);
  private readonly config = inject(ApplicationConfigService);
  private readonly tokenStorage = inject(TokenStorageService);

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}
  private initialized = false;

  init(): Promise<void | AuthState> {
    if (this.initialized || !isPlatformBrowser(this.platformId)) {
      return Promise.resolve(); // Skip initialization if already done or running on the server
    }

    this.initialized = true;
    this.store.dispatch(RefreshTokenActions.request());

    const authState$ = this.store.select(AuthSelectors.selectAuth).pipe(
      filter(
        (auth) =>
          auth.token === TokenStatus.INVALID ||
          (auth.token === TokenStatus.VALID && !!auth.user)
      ),
      take(1)
    );

    return lastValueFrom(authState$);
  }

  /**
   * Performs a request with user credentials
   * in order to get auth tokens
   *
   * @param { Login } credentials
   * @returns Observable<Login>
   */

  login(username: string, password: string): Observable<any> {
    return this.http.post(this.config.getEndpointFor('auth/login'), {
      username,
      password,
    });
  }

  logout(): Observable<void> {
    return this.http
      .post<void>(this.config.getEndpointFor('auth/sign-out'), {
        token: this.tokenStorage.getAccessToken(),
      })
      .pipe(
        map((response) => {
          console.log(response);
          this.tokenStorage.removeTokens();
        })
      );
  }

  refreshToken(): Observable<Object> {
    const token = this.tokenStorage.getAccessToken();
    if (!token) {
      return throwError(() => new Error('Token does not exists'));
    }

    return this.http.post(this.config.getEndpointFor('auth/refresh'), {
      refreshToken: token,
    });
  }

  getAuthUser(): Observable<AuthUser> {
    return this.http.get<AuthUser>(this.config.getEndpointFor('api/account/'));
  }
}
export const authServiceInitProvider: Provider = {
  provide: APP_INITIALIZER,
  useFactory: (authService: AuthService) => () =>
    new Promise<void | AuthState>((resolve) => {
      if (typeof window !== 'undefined') {
        window.addEventListener('load', () => {
          authService.init();
        });
      } else {
        resolve();
      }
    }),
  deps: [AuthService],
  multi: true,
};
