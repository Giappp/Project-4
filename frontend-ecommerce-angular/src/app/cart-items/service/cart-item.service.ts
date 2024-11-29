import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cartItem } from '../../interfaces/cart-item';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../../config/config';

@Injectable({
  providedIn: 'root'
})
export class cartItemService {

  constructor(private http: HttpClient) { }

  getAllcartItems(): Observable<cartItem[]> {
    return this.http.get<cartItem[]>(`${API_BASE_URL}/cart/1`);
  }

  getTotalQuantity(): Observable<number> {
    return this.http.get<number>(`${API_BASE_URL}/cart/1/totalPrice`);
  }
}
