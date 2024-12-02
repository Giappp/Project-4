import { ActionReducerMap } from '@ngrx/store';
import { CartState } from './cart/store/cart.model';
import { cartReducer } from './cart/store/cart.reducer';
import { AuthState } from './auth/store/auth.model';
import { authReducer } from './auth/store/auth.reducer';

export interface Appstate {
  cart: CartState;
  auth: AuthState;
}

export const reducers: ActionReducerMap<Appstate> = {
  cart: cartReducer,
  auth: authReducer,
};
