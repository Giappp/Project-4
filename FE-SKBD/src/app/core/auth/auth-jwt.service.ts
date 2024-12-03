import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ApplicationConfigService } from '../config/application-config.service';
import { map, Observable } from 'rxjs';
import { Login } from '../../model/login';
import { Register } from '../../model/register';
@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  private http = inject(HttpClient);
  private applicationConfig = inject(ApplicationConfigService);

  getToken(): string {
    return '';
  }

  login(credentials: Login): Observable<void> {
    return this.http
      .post(this.applicationConfig.getEndpointFor('auth/login'), credentials, {
        headers: this.getHttpHeaders(),
      })
      .pipe(
        map((response) => {
          this.authentiateSuccess(response, credentials.rememberMe);
        })
      );
  }

  logout(): Observable<void> {
    return this.http
      .post(
        this.applicationConfig.getEndpointFor('auth/sign-out'),
        { token: this.getToken() },
        { headers: this.getHttpHeaders() }
      )
      .pipe(map(() => {}));
  }

  register(model: Register): Observable<void> {
    return this.http
      .post(this.applicationConfig.getEndpointFor('auth/registration'), model, {
        headers: this.getHttpHeaders(),
      })
      .pipe(
        map((response) => {
          this.authentiateSuccess(response, false);
        })
      );
  }

  private authentiateSuccess(response: any, rememberMe: boolean): void {}
  private getHttpHeaders(extraHeaders?: {
    [key: string]: string;
  }): HttpHeaders {
    const token = this.getToken();
    const defaultHeaders = {
      'Content-Type': 'application/json',
    };
    return new HttpHeaders({ ...defaultHeaders, ...extraHeaders });
  }
}
