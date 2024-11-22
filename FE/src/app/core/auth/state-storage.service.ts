import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class StateStorageService {
  private previousUrlKey = 'previousUrl';
  private authenticationKey = 'SKBD-authenticationToken';

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  storeUrl(url: string): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.setItem(this.previousUrlKey, JSON.stringify(url));
    }
  }

  getUrl(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      const previousUrl = sessionStorage.getItem(this.previousUrlKey);
      return previousUrl ? JSON.parse(previousUrl) as string : null;
    }
    return null; // Return null if not in a browser context
  }

  clearUrl(): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.removeItem(this.previousUrlKey);
    }
  }

  storeAuthenticationToken(authenticationToken: string, rememberMe: boolean): void {
    authenticationToken = JSON.stringify(authenticationToken);
    this.clearAuthenticationToken();

    if (isPlatformBrowser(this.platformId)) {
      if (rememberMe) {
        localStorage.setItem(this.authenticationKey, authenticationToken);
      } else {
        sessionStorage.setItem(this.authenticationKey, authenticationToken);
      }
    }
  }

  getAuthenticationToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      const tokenFromSession = sessionStorage.getItem(this.authenticationKey);
      if (tokenFromSession) {
        return JSON.parse(tokenFromSession) as string;
      }
      const tokenFromLocal = localStorage.getItem(this.authenticationKey);
      return tokenFromLocal ? JSON.parse(tokenFromLocal) as string : null;
    }
    return null; // Return null if not in a browser context
  }

  clearAuthenticationToken(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(this.authenticationKey);
      sessionStorage.removeItem(this.authenticationKey);
    }
  }
}
