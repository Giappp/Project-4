import {
  createAction,
  createActionGroup,
  emptyProps,
  props,
} from '@ngrx/store';
import { AuthUser } from './auth.model';

// Login
export const LoginActions = createActionGroup({
  source: 'Auth: Login',
  events: {
    request: props<{
      username: string;
      password: string;
      rememberMe: boolean;
    }>(),
    success: emptyProps(),
    failure: props<{ error: Error }>(),
  },
});

// Logout
export const LogoutAction = createAction('[Auth] Logout');

// Auth User: me
export const AuthUserActions = createActionGroup({
  source: 'Auth: Auth User',
  events: {
    request: emptyProps(),
    success: props<{ user: AuthUser }>(),
    failure: emptyProps(),
  },
});

// Refresh token
export const RefreshTokenActions = createActionGroup({
  source: 'Auth: Refresh Token',
  events: {
    request: emptyProps(),
    success: emptyProps(),
    failure: emptyProps(),
  },
});

export const RegisterAction = createActionGroup({
  source: 'Auth: Register User',
  events: {},
});
