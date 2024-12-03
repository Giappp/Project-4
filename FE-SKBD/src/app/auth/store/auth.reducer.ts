import { Action, createReducer, on } from '@ngrx/store';
import { AuthState, TokenStatus } from './auth.model';
import {
  AuthUserActions,
  LoginActions,
  LogoutAction,
  RefreshTokenActions,
} from './auth.action';

export const AUTH_FEATURE_KEY = 'auth';

export interface AuthPartialState {
  readonly [AUTH_FEATURE_KEY]: AuthState;
}

export const initialAuthState: AuthState = {
  isLoggedIn: false,
  user: undefined,
  token: TokenStatus.PENDING,
  isLoadingLogin: false,
  hasLoginError: false,
};

const reducer = createReducer(
  initialAuthState,

  // Login
  on(LoginActions.request, (state) => ({ ...state, error: null })),
  on(LoginActions.success, (state) => ({ ...state, token: TokenStatus.VALID })),
  on(LoginActions.failure, (state, { error }) => ({ ...state, error })),

  // Logout
  on(LogoutAction, () => initialAuthState),

  // Auth User
  on(AuthUserActions.success, (state, { user }) => ({ ...state, user })),
  on(AuthUserActions.failure, (state) => ({ ...state, user: null })),

  // Refresh Token
  on(RefreshTokenActions.request, (state) => ({ ...state, error: null })),
  on(RefreshTokenActions.success, (state) => ({
    ...state,
    token: TokenStatus.VALID,
  })),
  on(RefreshTokenActions.failure, (state) => ({
    ...state,
    token: TokenStatus.INVALID,
  }))
);

export function authReducer(
  state: AuthState | undefined,
  action: Action
): AuthState {
  return reducer(state, action);
}
