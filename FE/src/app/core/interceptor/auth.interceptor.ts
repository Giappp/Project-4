import { Injectable, inject, PLATFORM_ID, Inject } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { StateStorageService } from '../auth/state-storage.service';
import { ApplicationConfigService } from '../config/application-config.service';
import { isPlatformBrowser } from '@angular/common';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private readonly stateStorageService = inject(StateStorageService);
  private readonly applicationConfigService = inject(ApplicationConfigService);

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    console.log('Adding authorization header');

    const serverApiUrl = this.applicationConfigService.getEndpointFor('');

    if (
      !request.url ||
      (request.url.startsWith('http') &&
        !(serverApiUrl && request.url.startsWith(serverApiUrl)))
    ) {
      return next.handle(request);
    }

    let token: string | null = null;

    if (isPlatformBrowser(this.platformId)) {
      // Access sessionStorage only if in browser context
      token = sessionStorage.getItem('token');
    }

    if (!token) {
      // Optionally retrieve the token from state storage if not in browser
      token = this.stateStorageService.getAuthenticationToken();
    }

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
