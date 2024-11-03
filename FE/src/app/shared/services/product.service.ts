import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { catchError, Observable, of, shareReplay } from 'rxjs';
import { Product } from '../../model/product';
import { Category } from '../../model/category';
import { Gender } from '../../model/gender';

@Injectable({
  providedIn: 'root',
})
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
        .get<Product[]>(`${this.baseUrl}/clothes`, {
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
    return this.httpClient.get<Product>(`${this.baseUrl}/clothes/${id}`, {
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
  }
}
