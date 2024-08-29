import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductServiceService {
  baseUrl!: string;
  constructor(private httpClient: HttpClient) {
    this.baseUrl = "http://localhost:3000/";
  };

  public getAllProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.baseUrl}clothes`, { headers: this.getHeader() });
  }

  getProductById(id: number): Observable<any> {
    return this.httpClient.get<Product>(`${this.baseUrl}clothes/${id}`, { headers: this.getHeader() });
  }

  private getHeader() {
    const header = new HttpHeaders()
      .set('Content-Type', 'application/json');
    return header;
  }
}
