import { inject, Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { AuthService } from '../services/auth.service';
import {
  AuthUserActions,
  LoginActions,
  LogoutAction,
  RefreshTokenActions,
} from './auth.action';
import {
  catchError,
  exhaustMap,
  finalize,
  map,
  merge,
  mergeMap,
  of,
  tap,
} from 'rxjs';
import { TokenStorageService } from '../../core/auth/token-storage.service';

@Injectable()
export class AuthEffects {
  private readonly router = inject(Router);
  private readonly actions$ = inject(Actions);
  private readonly authService = inject(AuthService);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly tokenStorageService = inject(TokenStorageService);

  readonly login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LoginActions.request),
      mergeMap(({ username, password, rememberMe }) =>
        this.authService.login(username, password).pipe(
          tap((response) => {
            this.tokenStorageService.saveAccessToken(response.data.token);
          }),
          map(() => LoginActions.success()),
          catchError((error) => of(LoginActions.failure({ error })))
        )
      )
    )
  );

  readonly onLoginSuccess$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(LoginActions.success),
      map(() => {
        // redirect to return url or home
        this.router.navigateByUrl(
          this.activatedRoute.snapshot.queryParams['returnUrl'] || '/'
        );
        return AuthUserActions.request();
      })
    );
  });

  readonly logout$ = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(LogoutAction),
        exhaustMap(() => {
          this.router.navigateByUrl('/');
          return this.authService
            .logout()
            .pipe(finalize(() => this.tokenStorageService.removeTokens()));
        })
      );
    },
    { dispatch: false }
  );

  readonly refreshToken$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(RefreshTokenActions.request),
      exhaustMap(() =>
        this.authService.refreshToken().pipe(
          map((data: any) => {
            // save tokens
            console.log(data);
            this.tokenStorageService.saveTokens(data.data.token);
            // trigger refresh token success action
            return RefreshTokenActions.success();
          }),
          catchError(() => of(RefreshTokenActions.failure()))
        )
      )
    );
  });

  readonly authUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(RefreshTokenActions.success, AuthUserActions.request),
      exhaustMap(() =>
        this.authService.getAuthUser().pipe(
          map((user) => {
            console.log(user);
            return AuthUserActions.success({ user });
          }),
          catchError(() => of(AuthUserActions.failure()))
        )
      )
    )
  );
  readonly onLoginOrRefreshTokenFailure$ = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(LoginActions.failure, RefreshTokenActions.failure),
        tap(() => this.tokenStorageService.removeTokens())
      );
    },
    { dispatch: false }
  );
}
