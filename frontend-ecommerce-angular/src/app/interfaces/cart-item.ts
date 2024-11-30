import { Product } from './product';
import { Order } from './order';
import { cart } from './cart';

export interface cartItem {
  id: number;
  cart: cart;
  product: Product;
  quantity: number;
  totalExcludeTaxe: number;
  totalWithTaxe: number;
  order?: Order;
}
