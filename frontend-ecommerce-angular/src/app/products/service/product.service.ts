import { Injectable } from '@angular/core';
import { Product } from '../../interfaces/product';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../../config/config';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${API_BASE_URL}/products`);
  }

  // getAllProducts(): Observable<Product[]> {
  //   let jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYW1lIiwicm9sZXMiOlsiQURNSU4iLCJVU0VSIl0sImV4cCI6MTcxOTU4OTQ0M30.162G_IqEXMsgC6rQyF6JPu46zoOHkuwbj1eyxpC8C3Q";
  //   // let jwt = this.authService.getToken();
  //   jwt = "Bearer " + jwt;
  //   let httpHeaders = new HttpHeaders({ "Authorization": jwt })

  //   return this.http.get<Product[]>(`${API_BASE_URL}/products`, { headers: httpHeaders });
  // }


  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${API_BASE_URL}/products/create`, product);
  }

  deleteProduct(id: number): Observable<Product> {
    return this.http.delete<Product>(`${API_BASE_URL}/products/delete/${id}`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${API_BASE_URL}/products/${id}`);
  }

  updateProduct(product: Product): Observable<Product> {
    return this.http.put<Product>(`${API_BASE_URL}/products/update/${product.idProd}`, product);
  }
}
