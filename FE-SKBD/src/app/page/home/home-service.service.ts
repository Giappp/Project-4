import { inject, Injectable } from '@angular/core';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { NewArrivalsProducts } from '../../model/new-arrivals';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class HomeService {
  private readonly config: ApplicationConfigService = inject(
    ApplicationConfigService
  );
  private readonly http = inject(HttpClient);

  getNewArrivalsProducts(): Observable<NewArrivalsProducts[]> {
    return this.http.get<NewArrivalsProducts[]>(
      this.config.getEndpointFor('api/products/new-arrivals')
    );
  }
}
