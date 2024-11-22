import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { StateStorageService } from './state-storage.service';
import { ApplicationConfigService } from '../config/application-config.service';
import { Observable } from 'rxjs';
import { Gender } from '../../model/gender';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private http = inject(HttpClient);
  private stateStorageService = inject(StateStorageService);
  private applicationConfig = inject(ApplicationConfigService);

  getProductGenders(): Observable<Gender[]> {
    const token = this.stateStorageService.getAuthenticationToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` // Include the token in the header
    });

    return this.http.get<Gender[]>(this.applicationConfig.getEndpointFor('api/products/gender'), { headers });
  }
}
