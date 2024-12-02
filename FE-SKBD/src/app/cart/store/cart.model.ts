export interface CartItem {
  id: number;
  name: string;
  quantity: number;
  price: number;
}

export interface CartState {
  items: CartItem[];
}
