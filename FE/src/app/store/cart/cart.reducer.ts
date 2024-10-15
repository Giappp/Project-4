import { createReducer, on } from '@ngrx/store';
import { CartState } from './cart.model';
import { addItem, clearCart, removeItem } from './cart.action';
import { Statement } from '@angular/compiler';

const initialState: CartState = {
  items: [],
};

export const cartReducer = createReducer(
  initialState,
  on(addItem, (state, { item }) => ({
    ...state, // Copy the existing state
    items: [...state.items, item], // Create a new items array with the added item
  })),
  on(removeItem, (state, { id }) => ({
    ...state, // Copy the existing state
    items: state.items.filter((item) => item.id !== id), // Return a new array excluding the removed item
  })),
  on(clearCart, (state) => ({
    ...state,
    items: [],
  }))
);
