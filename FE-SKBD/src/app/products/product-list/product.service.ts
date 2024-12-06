import { inject, Injectable } from '@angular/core';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { ProductFilter } from './product-filter';
import { Observable } from 'rxjs';
import { Product } from './product.interface';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FilterOptions } from './filter-options';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private applicationConfig = inject(ApplicationConfigService);

  constructor(private http: HttpClient) {}
  getProducts(filter: ProductFilter): Observable<any> {
    return this.http.post<{ items: Product[]; total: number }>(
      this.applicationConfig.getEndpointFor('api/products/search'),
      { filter }
    );
  }
  getFilterOptions(): Observable<FilterOptions> {
    return this.http.get<FilterOptions>(
      this.applicationConfig.getEndpointFor('api/products/filters')
    );
  }
}
