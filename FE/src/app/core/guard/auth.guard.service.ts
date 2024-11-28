import { inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AccountService } from '../auth/account.service';
import { StateStorageService } from '../auth/state-storage.service';
import { map } from 'rxjs';

export const authGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const accountService = inject(AccountService);
  const router = inject(Router);
  const stateStorageService = inject(StateStorageService);

  return accountService.identity().pipe(
    map((account) => {
      if (account) {
        const { authorities } = next.data; // Get required authorities from route

        // If no authorities required or the user has the necessary authorities
        if (!authorities || accountService.hasAnyAuthority(authorities)) {
          return true;  // Allow access
        }

        // If user does not have the required authorities
        router.navigate(['accessdenied']);
        return false;
      }

      // If user is not authenticated, store the current URL
      stateStorageService.storeUrl(state.url);

      // Redirect to login page
      router.navigate(['/auth/login']);
      return false;
    })
  );
};
