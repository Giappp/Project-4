<<<<<<< Updated upstream
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { catchError, Observable, of, shareReplay } from 'rxjs';
import { Product } from '../../model/product';
=======
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
>>>>>>> Stashed changes
import { Category } from '../../model/category';
import { Gender } from '../../model/gender';

@Injectable({
  providedIn: 'root',
})
<<<<<<< Updated upstream
export class ProductService implements OnInit {
  baseUrl!: string;
  private productCache$!: Observable<Product[]>;
  private categoryCache$!: Observable<Category[]>;
  private genderCache$!: Observable<Gender[]>;
  constructor(private httpClient: HttpClient) {
    this.baseUrl = 'http://localhost:3000';
  }
  ngOnInit(): void {}

  public getAllProducts(): Observable<Product[]> {
    // Check if the cache exists, if not, make the API call an cache it
    if (!this.productCache$) {
      this.productCache$ = this.httpClient
        .get<Product[]>(`${this.baseUrl}/products`, {
          headers: this.getHeader(),
        })
        .pipe(
          shareReplay(1),
          catchError((error) => {
            // Reset cache in case of error and handle error as needed
            this.productCache$ = null as any;
            return of([]);
          })
        );
    }
    return this.productCache$;
  }

  getProductById(id: number): Observable<any> {
    return this.httpClient.get<Product>(`${this.baseUrl}/products/${id}`, {
      headers: this.getHeader(),
    });
  }

  public getAllProductsCategories(): Observable<Category[]> {
    if (!this.categoryCache$) {
      this.categoryCache$ = this.httpClient
        .get<Category[]>(`${this.baseUrl}/categories`, {
          headers: this.getHeader(),
        })
        .pipe(
          shareReplay(1),
          catchError((error) => {
            this.categoryCache$ = null as any;
            return of([]);
          })
        );
    }
    return this.categoryCache$;
  }

  public getProductGender(): Observable<Gender[]> {
    if (!this.genderCache$) {
      this.genderCache$ = this.httpClient
        .get<Gender[]>(`${this.baseUrl}/genders`, {
          headers: this.getHeader(),
        })
        .pipe(
          shareReplay(1),
          catchError((error) => {
            this.genderCache$ = null as any;
            console.log(error);
            return of([]);
          })
        );
    }
    return this.genderCache$;
  }

  private getHeader() {
    const header = new HttpHeaders().set('Content-Type', 'application/json');
    return header;
=======
export class ProductService {
  private apiUrl = 'http://localhost:8083/api/products'; // Base URL for your API

  constructor(private http: HttpClient) {}

  // Method to get all product categories from the API
  getAllProductsCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/ca tegory`); // Adjust the endpoint as needed
  }

  // Method to get product gender as an observable array
  getProductGender(): Observable<Gender[]> {
    return this.http.get<Gender>(`${this.apiUrl}/gender`).pipe(
      map((gender) => [gender]) // Wrap the single Gender object in an array
    );
>>>>>>> Stashed changes
  }
}
