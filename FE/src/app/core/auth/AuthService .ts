import {Inject, Injectable, PLATFORM_ID} from '@angular/core';
import { AuthServerProvider } from './auth-server-provider'; // Import the AuthServerProvider
import { StateStorageService } from './state-storage.service';
import { Login } from "../../model/login";
import {Register} from "../../model/register";
import {catchError, Observable, tap} from "rxjs";
import {isPlatformBrowser} from "@angular/common";

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(
    private authServer: AuthServerProvider, // Inject the AuthServerProvider
    private stateStorageService: StateStorageService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
  }

  login(credentials: Login): void {
    this.authServer.login(credentials).subscribe({
      next: () => {
        // Handle successful login
        const token = this.stateStorageService.getAuthenticationToken();
        if (token) {
          // Use the token for API requests or redirect the user
          console.log('Login successful, token retrieved:', token);
        }
      },
      error: (err) => {
        console.error('Login failed', err);
      }
    });
  }

  logout(): void {
    this.authServer.logout().subscribe({
      next: () => {
        this.stateStorageService.clearAuthenticationToken();
        // Optionally redirect or notify the user
        console.log('Successfully logged out');
      },
      error: (err) => {
        console.error('Logout failed', err);
      }
    });
  }


  register(model: Register): Observable<any> {
    return this.authServer.register(model).pipe(
      tap(() => {
        console.log('Registration successful');
      }),
      catchError(err => {
        console.error('Registration failed', err);
        throw err; // Re-throw the error for further handling
      })
    );
  }
  setSessionData(key: string, value: string) {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.setItem(key, value);
    } else {
      console.warn('sessionStorage is not available on the server.');
    }
  }

  getSessionData(key: string): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return sessionStorage.getItem(key);
    }
    return null;
  }
}
