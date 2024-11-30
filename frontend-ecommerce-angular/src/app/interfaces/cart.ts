import { cartItem } from "./cart-item";

export interface cart {
  id: number;
  namecart: string;
  totalPrice: number;
  cartItems: cartItem[];
  totalItems: number;
}
