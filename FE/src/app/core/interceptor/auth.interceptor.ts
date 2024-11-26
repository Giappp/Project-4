import {Inject, inject, Injectable, PLATFORM_ID} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {StateStorageService} from "../auth/StateStorageService";
import {ApplicationConfigService} from "../config/application-config.service";
import {Observable} from "rxjs";
import {isPlatformBrowser} from "@angular/common";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private readonly stateStorageService = inject(StateStorageService);
  private readonly applicationConfigService = inject(ApplicationConfigService);

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (isPlatformBrowser(this.platformId)) {
      console.log('Adding authorization header');
      const serverApiUrl = this.applicationConfigService.getEndpointFor('');
      if (
        !request.url ||
        (request.url.startsWith('http') &&
          !(serverApiUrl && request.url.startsWith(serverApiUrl)))
      ) {
        return next.handle(request);
      }

      const token: string | null =
        this.stateStorageService.getAuthenticationToken();
      if (token) {
        request = request.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`,
          },
        });
      }
    }
    return next.handle(request);
  }
}
