import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { Category } from '../../model/category';

@Injectable({ providedIn: 'root' })
export class HeaderService {
  private http = inject(HttpClient);
  private applicationConfig = inject(ApplicationConfigService);

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(
      this.applicationConfig.getEndpointFor('api/products/genders')
    );
  }
}
