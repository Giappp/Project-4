import { ActionReducerMap } from '@ngrx/store';
import { CartState } from './cart/cart.model';
import { cartReducer } from './cart/cart.reducer';

export interface Appstate {
  cart: CartState;
}

export const reducers: ActionReducerMap<Appstate> = {
  cart: cartReducer,
};
