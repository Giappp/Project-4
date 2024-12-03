import { inject, Injectable } from '@angular/core';
import { Store } from '@ngrx/store';

import * as AuthSelectors from './auth.selectors';
import { AuthUserActions, LoginActions, LogoutAction } from './auth.action';

@Injectable({ providedIn: 'root' })
export class AuthFacade {
  private readonly store = inject(Store);

  readonly authUser$ = this.store.select(AuthSelectors.selectUser);
  readonly isLoggedIn$ = this.store.select(AuthSelectors.selectIsLoggedIn);
  readonly isLoadingLogin$ = this.store.select(
    AuthSelectors.selectIsLoadingLogin
  );
  readonly hasLoginError$ = this.store.select(AuthSelectors.selectError);

  login(username: string, password: string, rememberMe: boolean) {
    this.store.dispatch(
      LoginActions.request({ username, password, rememberMe })
    );
  }

  logout() {
    this.store.dispatch(LogoutAction());
  }

  getAuthUser() {
    this.store.dispatch(AuthUserActions.request());
  }
}
