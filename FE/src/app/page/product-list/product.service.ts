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
  getProducts(
    filter: ProductFilter
  ): Observable<{ items: Product[]; total: number }> {
    let params = new HttpParams()
      .set('page', filter.page.toString())
      .set('pageSize', filter.pageSize.toString());
    if (filter.minPrice)
      params = params.set('minPrice', filter.minPrice.toString());
    if (filter.maxPrice)
      params = params.set('maxPrice', filter.maxPrice.toString());
    if (filter.sizes?.length)
      params = params.set('sizes', filter.sizes.join(','));
    if (filter.colors?.length)
      params = params.set('colors', filter.colors.join(','));
    if (filter.type) params = params.set('type', filter.type);
    return this.http.get<{ items: Product[]; total: number }>(
      this.applicationConfig.getEndpointFor('api/products'),
      { params }
    );
  }
  getFilterOptions(): Observable<FilterOptions> {
    return this.http.get<FilterOptions>(
      this.applicationConfig.getEndpointFor('api/products/filter-options')
    );
  }
}
