import { Injectable, inject } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../config/application-config.service';
import { TokenStorageService } from '../auth/token-storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private readonly stateStorageService = inject(TokenStorageService);
  private readonly applicationConfigService = inject(ApplicationConfigService);

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const serverApiUrl = this.applicationConfigService.getEndpointFor('');
    if (
      !request.url ||
      (request.url.startsWith('http') &&
        !(serverApiUrl && request.url.startsWith(serverApiUrl)))
    ) {
      return next.handle(request);
    }

    const token: string | null = this.stateStorageService.getAccessToken();
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    return next.handle(request);
  }
}
