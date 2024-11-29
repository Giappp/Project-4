import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const excludeArray = ["/login", "/register", '/verifyEmail'];

    // Check if the request URL should be excluded from token attachment
    if (!excludeArray.some(url => request.url.includes(url))) {
      const jwt = this.authService.getToken();

      // Only add the Authorization header if the token is available
      if (jwt) {
        const reqWithToken = request.clone({
          setHeaders: { Authorization: `Bearer ${jwt}` } // Include 'Bearer' prefix
        });
        return next.handle(reqWithToken);
      }
    }

    // Proceed without modification if the token is not added
    return next.handle(request);
  }
}