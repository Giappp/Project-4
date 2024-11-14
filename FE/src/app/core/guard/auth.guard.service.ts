import { inject, Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AccountService } from '../auth/account.service';
import { StateStorageService } from '../auth/state-storage.service';
import { map } from 'rxjs';

export const authGuard: CanActivateFn = (
  next: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const accountService = inject(AccountService);
  const router = inject(Router);
  const stateStorageService = inject(StateStorageService);
  return accountService.identity().pipe(
    map((account) => {
      if (account) {
        const { authorities } = next.data;

        if (
          !authorities ||
          authorities.length === 0 ||
          accountService.hasAnyAuthority(authorities)
        ) {
          return true;
        }

        router.navigate(['accessdenied']);
        return false;
      }

      stateStorageService.storeUrl(state.url);
      router.navigate(['/login']);
      return false;
    })
  );
};
