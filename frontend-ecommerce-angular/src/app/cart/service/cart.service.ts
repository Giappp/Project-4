import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cart } from '../../interfaces/cart';
import { API_BASE_URL } from '../../config/config';
import { cartItem } from '../../interfaces/cart-item';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }

  addcart(cart: cart): Observable<cart> {
    return this.http.post<cart>(`${API_BASE_URL}/orders/create`, cart);
  }

  deletecart(id: number): Observable<cart> {
    return this.http.delete<cart>(`${API_BASE_URL}/cart/${id}`);
  }

  getcartById(id: number): Observable<cart> {
    return this.http.get<cart>(`${API_BASE_URL}/cart/${id}`);
  }


  updatecart(cart: cart): Observable<cart> {
    return this.http.put<cart>(`${API_BASE_URL}/cart/${cart.id}`, cart);
  }


  getTotalPrice(): Observable<number> {
    return this.http.get<number>(`${API_BASE_URL}/cart/1/totalPrice`);
  }

  addItemTocart(cartItem: cartItem): Observable<cartItem> {
    return this.http.post<cartItem>(`${API_BASE_URL}/cart/items`, cartItem);
  }

  removeItemFromcart(cartItem: cartItem): Observable<void> {
    return this.http.delete<void>(`${API_BASE_URL}/cart/1/items/1`, { body: cartItem });
  }

  clearAllcartItems(): Observable<void> {
    return this.http.delete<void>(`${API_BASE_URL}/cart/1`);
  }


}
