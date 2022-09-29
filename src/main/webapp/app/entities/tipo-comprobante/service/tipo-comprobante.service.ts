import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoComprobante, NewTipoComprobante } from '../tipo-comprobante.model';

export type PartialUpdateTipoComprobante = Partial<ITipoComprobante> & Pick<ITipoComprobante, 'id'>;

export type EntityResponseType = HttpResponse<ITipoComprobante>;
export type EntityArrayResponseType = HttpResponse<ITipoComprobante[]>;

@Injectable({ providedIn: 'root' })
export class TipoComprobanteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-comprobantes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tipoComprobante: NewTipoComprobante): Observable<EntityResponseType> {
    return this.http.post<ITipoComprobante>(this.resourceUrl, tipoComprobante, { observe: 'response' });
  }

  update(tipoComprobante: ITipoComprobante): Observable<EntityResponseType> {
    return this.http.put<ITipoComprobante>(`${this.resourceUrl}/${this.getTipoComprobanteIdentifier(tipoComprobante)}`, tipoComprobante, {
      observe: 'response',
    });
  }

  partialUpdate(tipoComprobante: PartialUpdateTipoComprobante): Observable<EntityResponseType> {
    return this.http.patch<ITipoComprobante>(`${this.resourceUrl}/${this.getTipoComprobanteIdentifier(tipoComprobante)}`, tipoComprobante, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoComprobante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoComprobante[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTipoComprobanteIdentifier(tipoComprobante: Pick<ITipoComprobante, 'id'>): number {
    return tipoComprobante.id;
  }

  compareTipoComprobante(o1: Pick<ITipoComprobante, 'id'> | null, o2: Pick<ITipoComprobante, 'id'> | null): boolean {
    return o1 && o2 ? this.getTipoComprobanteIdentifier(o1) === this.getTipoComprobanteIdentifier(o2) : o1 === o2;
  }

  addTipoComprobanteToCollectionIfMissing<Type extends Pick<ITipoComprobante, 'id'>>(
    tipoComprobanteCollection: Type[],
    ...tipoComprobantesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tipoComprobantes: Type[] = tipoComprobantesToCheck.filter(isPresent);
    if (tipoComprobantes.length > 0) {
      const tipoComprobanteCollectionIdentifiers = tipoComprobanteCollection.map(
        tipoComprobanteItem => this.getTipoComprobanteIdentifier(tipoComprobanteItem)!
      );
      const tipoComprobantesToAdd = tipoComprobantes.filter(tipoComprobanteItem => {
        const tipoComprobanteIdentifier = this.getTipoComprobanteIdentifier(tipoComprobanteItem);
        if (tipoComprobanteCollectionIdentifiers.includes(tipoComprobanteIdentifier)) {
          return false;
        }
        tipoComprobanteCollectionIdentifiers.push(tipoComprobanteIdentifier);
        return true;
      });
      return [...tipoComprobantesToAdd, ...tipoComprobanteCollection];
    }
    return tipoComprobanteCollection;
  }
}
