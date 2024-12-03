import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, EMPTY, Observable, throwError } from 'rxjs';
import { AuthFacade } from '../store/auth.fascade';
import { TokenStorageService } from '../../core/auth/token-storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private readonly authFacade = inject(AuthFacade);
  private readonly tokenStorageService = inject(TokenStorageService);

  intercept(
    req: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const accessToken = this.tokenStorageService.getAccessToken();

    if (accessToken) {
      // Add the Authorization header to the request
      req = req.clone({
        setHeaders: { Authorization: `Bearer ${accessToken}` },
      });
    }

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        // try to avoid errors on logout
        // therefore we check the url path of '/auth/'
        const ignoreAPIs = ['/auth/'];
        if (ignoreAPIs.some((api) => req.url.includes(api))) {
          return throwError(() => error);
        }

        // Handle global error status
        switch (error.status) {
          case 401:
            return this.handle401();
          // Add more error status handling here (e.g. 403)
          default:
            // Rethrow the error as is
            return throwError(() => error);
        }
      })
    );
  }

  private handle401() {
    this.authFacade.logout();
    return EMPTY;
  }
}
