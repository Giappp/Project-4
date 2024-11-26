import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Category } from '../../model/category';
import { Gender } from '../../model/gender';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = 'http://localhost:8083/api/products'; // Base URL for your API

  constructor(private http: HttpClient) {}

  // Method to get all product categories from the API
  getAllProductsCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/category`); // Adjust the endpoint as needed
  }

  // Method to get product gender as an observable array
  getProductGender(): Observable<Gender[]> {
    return this.http.get<Gender>(`${this.apiUrl}/by-gender`).pipe(
      map((gender) => [gender]) // Wrap the single Gender object in an array
    );
  }
}
