import { HttpClient } from '@angular/common/http';
import { APP_INITIALIZER, inject, Injectable, Provider } from '@angular/core';
import { Store } from '@ngrx/store';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { StateStorageService } from '../../core/auth/state-storage.service';
import { AuthState, AuthUser, TokenStatus } from '../store/auth.model';
import { RefreshTokenActions } from '../store/auth.action';

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
import { Login } from '../../model/login';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly store = inject(Store);
  private readonly http = inject(HttpClient);
  private readonly config = inject(ApplicationConfigService);
  private readonly tokenStorage = inject(StateStorageService);

  init(): Promise<AuthState> {
    this.store.dispatch(RefreshTokenActions.request());

    const authState$ = this.store.select(AuthSelectors.selectAuth).pipe(
      filter((auth) => auth.token === TokenStatus.VALID && !auth.user),
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
        token: this.tokenStorage.getAuthenticationToken(),
      })
      .pipe(
        map((response) => {
          console.log(response);
          this.tokenStorage.clearAuthenticationToken();
        })
      );
  }

  refreshToken(): Observable<Object> {
    const token = this.tokenStorage.getAuthenticationToken();
    if (!token) {
      return throwError(() => new Error('Token does not exists'));
    }

    return this.http.post(this.config.getEndpointFor('auth/refresh'), token);
  }

  getAuthUser(): Observable<AuthUser> {
    return this.http.get<AuthUser>(this.config.getEndpointFor('api/account/'));
  }
}
export const authServiceInitProvider: Provider = {
  provide: APP_INITIALIZER,
  useFactory: (authService: AuthService) => () => authService.init(),
  deps: [AuthService],
  multi: true,
};
