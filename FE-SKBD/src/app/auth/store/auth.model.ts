export enum TokenStatus {
  PENDING = 'PENDING',
  VALIDATING = 'VALIDATING',
  VALID = 'VALID',
  INVALID = 'INVALID',
}

export interface AuthState {
  isLoggedIn: boolean;
  user?: AuthUser | null;
  token: TokenStatus;
  isLoadingLogin: boolean;
  hasLoginError: boolean;
}

export interface AuthUser {
  activated: boolean;
  firstName: string;
  lastName: string;
  amountAvailable: number;
  amountReserved: number;
  email: string;
  login: string;
  imageUrl: string;
}
