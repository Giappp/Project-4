import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class StateStorageService {
  private previousUrlKey = 'previousUrl';
  private authenticationKey = 'SKBD-authenticationToken';

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  storeUrl(url: string): void {
    if (this.isBrowser()) {
      sessionStorage.setItem(this.previousUrlKey, JSON.stringify(url));
    }
  }

  getUrl(): string | null {
    if (this.isBrowser()) {
      const previousUrl = sessionStorage.getItem(this.previousUrlKey);
      return previousUrl
        ? (JSON.parse(previousUrl) as string | null)
        : previousUrl;
    }
    return null;
  }

  clearUrl(): void {
    if (this.isBrowser()) {
      sessionStorage.removeItem(this.previousUrlKey);
    }
  }

  storeAuthenticationToken(authenticationToken: string, rememberMe: boolean): void {
    if (!this.isBrowser()) {
      return;
    }
    authenticationToken = JSON.stringify(authenticationToken);
    this.clearAuthenticationToken();
    if (rememberMe) {
      localStorage.setItem(this.authenticationKey, authenticationToken);
    } else {
      sessionStorage.setItem(this.authenticationKey, authenticationToken);
    }
  }

  getAuthenticationToken(): string | null {
    if (this.isBrowser()) {
      const authenticationToken = sessionStorage.getItem(this.authenticationKey);
      return authenticationToken
        ? (JSON.parse(authenticationToken) as string | null)
        : authenticationToken;
    }
    return null;
  }

  clearAuthenticationToken(): void {
    if (this.isBrowser()) {
      localStorage.removeItem(this.authenticationKey);
      sessionStorage.removeItem(this.authenticationKey);
    }
  }
}
