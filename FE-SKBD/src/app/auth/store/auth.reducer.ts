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

export const initialState: AuthState = {
  isLoggedIn: false,
  user: undefined,
  token: TokenStatus.PENDING,
  isLoadingLogin: false,
  hasLoginError: false,
};

const reducer = createReducer(
  initialState,

  // On Login Action
  on(
    LoginActions.request,
    (state): AuthState => ({
      ...state,
      token: TokenStatus.VALIDATING,
      isLoadingLogin: true,
      hasLoginError: false,
    })
  ),

  // Refresh Token
  on(
    RefreshTokenActions.request,
    (state): AuthState => ({
      ...state,
      isLoggedIn: true,
      token: TokenStatus.VALIDATING,
    })
  ),

  on(
    LoginActions.success,
    RefreshTokenActions.success,
    (state): AuthState => ({
      ...state,
      isLoggedIn: true,
      isLoadingLogin: false,
      token: TokenStatus.VALID,
    })
  ),

  on(
    LoginActions.failure,
    RefreshTokenActions.failure,
    (state, action): AuthState => ({
      ...state,
      isLoadingLogin: false,
      token: TokenStatus.INVALID,
      hasLoginError:
        action.type === LoginActions.failure.type && !!action.error,
    })
  ),

  // Logout
  on(
    LogoutAction,
    (): AuthState => ({
      ...initialState,
    })
  ),

  // Auth user
  on(
    AuthUserActions.success,
    (state, action): AuthState => ({
      ...state,
      user: action.user,
    })
  ),
  on(
    AuthUserActions.failure,
    (): AuthState => ({
      ...initialState,
    })
  )
);

export function authReducer(
  state: AuthState | undefined,
  action: Action
): AuthState {
  return reducer(state, action);
}
