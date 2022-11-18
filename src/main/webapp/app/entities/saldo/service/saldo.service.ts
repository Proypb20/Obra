import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DATE_FORMAT } from 'app/config/input.constants';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISaldo, NewSaldo } from '../saldo.model';

export type EntityResponseType = HttpResponse<ISaldo>;
export type EntityArrayResponseType = HttpResponse<ISaldo[]>;

@Injectable({ providedIn: 'root' })
export class SaldoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/saldo');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISaldo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  query(): Observable<EntityArrayResponseType> {
    return this.http.get<ISaldo[]>(this.resourceUrl, { observe: 'response' });
  }
}
