import { Inject, Injectable, PLATFORM_ID, inject } from '@angular/core';
import { LocalStorageService } from './local-storage.service';
import { isPlatformBrowser } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class TokenStorageService {
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}
  private readonly localStorageService = inject(LocalStorageService);

  private readonly accessTokenKey = 'SKBD-Token';

  getAccessToken(): string | null {
    if (!isPlatformBrowser(this.platformId)) {
      return null; // localStorage is not available on the server
    }
    return this.localStorageService.getItem(this.accessTokenKey) as string;
  }

  saveAccessToken(token: string) {
    this.localStorageService.setItem(this.accessTokenKey, token);
  }

  saveTokens(accessToken: string) {
    this.saveAccessToken(accessToken);
  }

  removeTokens() {
    this.localStorageService.removeItem(this.accessTokenKey);
  }
}
