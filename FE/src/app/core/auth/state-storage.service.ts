import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class StateStorageService {
  private previousUrlKey = 'previousUrl';
  private authenticationKey = 'SKBD-authenticationToken';

  storeUrl(url: string): void {
    sessionStorage.setItem(this.previousUrlKey, JSON.stringify(url));
  }

  getUrl(): string | null {
    const previousUrl = sessionStorage.getItem(this.previousUrlKey);
    return previousUrl
      ? (JSON.parse(previousUrl) as string | null)
      : previousUrl;
  }

  clearUrl(): void {
    sessionStorage.removeItem(this.previousUrlKey);
  }

  storeAuthenticationToken(authenticationToken: string, rememberMe: boolean) {
    authenticationToken = JSON.stringify(authenticationToken);
    this.clearAuthenticationToken();
    if (rememberMe) {
      localStorage.setItem(this.authenticationKey, authenticationToken);
    } else {
      sessionStorage.setItem(this.authenticationKey, authenticationToken);
    }
  }

  getAuthenticationToken(): string | null {
    const authenticationToken = sessionStorage.getItem(this.authenticationKey);
    return authenticationToken
      ? (JSON.parse(authenticationToken) as string | null)
      : authenticationToken;
  }
  clearAuthenticationToken() {
    localStorage.removeItem(this.authenticationKey);
    sessionStorage.removeItem(this.authenticationKey);
  }
}
