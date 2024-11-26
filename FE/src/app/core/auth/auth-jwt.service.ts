import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { StateStorageService } from './state-storage.service';
import { ApplicationConfigService } from '../config/application-config.service';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Login } from '../../model/login';
import { Register } from '../../model/register';
@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  private http = inject(HttpClient);
  private stateStorageService = inject(StateStorageService);
  private applicationConfig = inject(ApplicationConfigService);

  getToken(): string {
    return this.stateStorageService.getAuthenticationToken() ?? '';
  }
  login(credentials: Login): Observable<void> {
    return this.http
      .post(this.applicationConfig.getEndpointFor('auth/login'), credentials)
      .pipe(
        map((response) => {
          this.authentiateSuccess(response, credentials.rememberMe);
        })
      );
  }
  logout(): Observable<void> {
    const jwtToken = this.getToken();
    return this.http
      .post(this.applicationConfig.getEndpointFor('auth/sign-out'), {
        token: jwtToken,
      })
      .pipe(
        map(() => {
          this.stateStorageService.clearAuthenticationToken();
        })
      );
  }
  register(model: Register): Observable<void> {
    return this.http
      .post(this.applicationConfig.getEndpointFor('auth/registration'), model)
      .pipe(
        map((response) => {
          this.authentiateSuccess(response, false);
        })
      );
  }
  private authentiateSuccess(response: any, rememberMe: boolean): void {
    this.stateStorageService.storeAuthenticationToken(
      response.data.token,
      rememberMe
    );
  }
}
