import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class SubcoPayRepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subco-payments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  generateXLS(id: number): any {
    return this.http.get(`${this.resourceUrl}/${id}`, { responseType: 'blob' });
  }
}
