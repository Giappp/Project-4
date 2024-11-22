import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ApplicationConfigService {
  private apiEndPoint = 'http://localhost:8083';

  getEndpointFor(service: string): string {
    return `${this.apiEndPoint}/${service}`;
  }
}
