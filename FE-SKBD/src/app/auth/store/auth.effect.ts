import { inject, Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { AuthService } from '../services/auth.service';
import { StateStorageService } from '../../core/auth/state-storage.service';
import {
  AuthUserActions,
  LoginActions,
  LogoutAction,
  RefreshTokenActions,
} from './auth.action';
import { catchError, exhaustMap, finalize, map, of, tap } from 'rxjs';

@Injectable()
export class AuthEffects {
  private readonly router = inject(Router);
  private readonly actions$ = inject(Actions);
  private readonly authService = inject(AuthService);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly tokenStorageService = inject(StateStorageService);

  readonly login$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(LoginActions.request),
      exhaustMap((credentials) =>
        this.authService.login(credentials.username, credentials.password).pipe(
          map((response: any) => {
            this.tokenStorageService.storeAuthenticationToken(
              response.data.token,
              credentials.rememberMe
            );
            return LoginActions.success();
          }),
          catchError((error) => of(LoginActions.failure({ error })))
        )
      )
    );
  });

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
            .pipe(
              finalize(() =>
                this.tokenStorageService.clearAuthenticationToken()
              )
            );
        })
      );
    },
    { dispatch: false }
  );

  readonly getUser$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(LoginActions.success, AuthUserActions.request),
      exhaustMap(() => {
        return this.authService.getAuthUser().pipe(
          map((user) => AuthUserActions.success({ user })),
          catchError(() => of(AuthUserActions.failure()))
        );
      })
    );
  });

  readonly refreshToken$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(RefreshTokenActions.request),
      exhaustMap(() =>
        this.authService.refreshToken().pipe(
          map((response: any) => {
            this.tokenStorageService.storeAuthenticationToken(
              response.data.token,
              false
            );
            return RefreshTokenActions.success();
          }),
          catchError(() => of(RefreshTokenActions.failure()))
        )
      )
    );
  });

  readonly onLoginOrRefreshTokenFailure$ = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(LoginActions.failure, RefreshTokenActions.failure),
        tap(() => this.tokenStorageService.clearAuthenticationToken())
      );
    },
    { dispatch: false }
  );
}
