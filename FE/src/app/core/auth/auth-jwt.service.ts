import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { StateStorageService } from './state-storage.service';
import { ApplicationConfigService } from '../config/application-config.service';
import { map, Observable } from 'rxjs';
import { Login } from '../../model/login';
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
        map((response) =>
          this.authentiateSuccess(response, credentials.rememberMe)
        )
      );
  }
  logout(): Observable<void> {
    return new Observable((observer) => {
      this.stateStorageService.clearAuthenticationToken();
      observer.complete();
    });
  }
  private authentiateSuccess(response: any, rememberMe: boolean): void {
    console.log(response);
    this.stateStorageService.storeAuthenticationToken(
      response.data.token,
      rememberMe
    );
  }
}
