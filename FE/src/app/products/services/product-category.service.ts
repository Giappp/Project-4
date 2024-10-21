import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { catchError, Observable, of, shareReplay } from 'rxjs';
import { Product } from '../models/product';
import { ProductCategory } from '../models/product-categories';

@Injectable({
  providedIn: 'root',
})
export class ProductCategoryService implements OnInit {
  baseUrl!: string;
  private categoryCache$!: Observable<ProductCategory[]>;
  constructor(private httpClient: HttpClient) {
    this.baseUrl = 'http://localhost:3000/';
  }
  ngOnInit(): void {}

  public getAllProducts(): Observable<ProductCategory[]> {
    // Check if the cache exists, if not, make the API call an cache it
    if (!this.categoryCache$) {
      this.categoryCache$ = this.httpClient
        .get<ProductCategory[]>(`${this.baseUrl}categories`, {
          headers: this.getHeader(),
        })
        .pipe(
          shareReplay(1),
          catchError((error) => {
            // Reset cache in case of error and handle error as needed
            this.categoryCache$ = null as any;
            return of([]);
          })
        );
    }
    return this.categoryCache$;
  }

  private getHeader() {
    const header = new HttpHeaders().set('Content-Type', 'application/json');
    return header;
  }
}
