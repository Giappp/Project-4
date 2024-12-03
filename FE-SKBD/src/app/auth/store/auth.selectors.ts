import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AuthState } from './auth.model';
import { AUTH_FEATURE_KEY } from './auth.reducer';

export const selectAuth = createFeatureSelector<AuthState>(AUTH_FEATURE_KEY);

export const selectUser = createSelector(selectAuth, (state) => state.user);
export const selectToken = createSelector(selectAuth, (state) => state.token);
export const selectError = createSelector(
  selectAuth,
  (state) => state.hasLoginError
);
export const selectIsLoadingLogin = createSelector(
  selectAuth,
  (state) => state.isLoadingLogin
);
export const selectIsLoggedIn = createSelector(
  selectAuth,
  (state) => state.isLoggedIn
);
