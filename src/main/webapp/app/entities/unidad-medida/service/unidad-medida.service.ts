import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUnidadMedida, NewUnidadMedida } from '../unidad-medida.model';

export type PartialUpdateUnidadMedida = Partial<IUnidadMedida> & Pick<IUnidadMedida, 'id'>;

export type EntityResponseType = HttpResponse<IUnidadMedida>;
export type EntityArrayResponseType = HttpResponse<IUnidadMedida[]>;

@Injectable({ providedIn: 'root' })
export class UnidadMedidaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/unidad-medidas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(unidadMedida: NewUnidadMedida): Observable<EntityResponseType> {
    return this.http.post<IUnidadMedida>(this.resourceUrl, unidadMedida, { observe: 'response' });
  }

  update(unidadMedida: IUnidadMedida): Observable<EntityResponseType> {
    return this.http.put<IUnidadMedida>(`${this.resourceUrl}/${this.getUnidadMedidaIdentifier(unidadMedida)}`, unidadMedida, {
      observe: 'response',
    });
  }

  partialUpdate(unidadMedida: PartialUpdateUnidadMedida): Observable<EntityResponseType> {
    return this.http.patch<IUnidadMedida>(`${this.resourceUrl}/${this.getUnidadMedidaIdentifier(unidadMedida)}`, unidadMedida, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnidadMedida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnidadMedida[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUnidadMedidaIdentifier(unidadMedida: Pick<IUnidadMedida, 'id'>): number {
    return unidadMedida.id;
  }

  compareUnidadMedida(o1: Pick<IUnidadMedida, 'id'> | null, o2: Pick<IUnidadMedida, 'id'> | null): boolean {
    return o1 && o2 ? this.getUnidadMedidaIdentifier(o1) === this.getUnidadMedidaIdentifier(o2) : o1 === o2;
  }

  addUnidadMedidaToCollectionIfMissing<Type extends Pick<IUnidadMedida, 'id'>>(
    unidadMedidaCollection: Type[],
    ...unidadMedidasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const unidadMedidas: Type[] = unidadMedidasToCheck.filter(isPresent);
    if (unidadMedidas.length > 0) {
      const unidadMedidaCollectionIdentifiers = unidadMedidaCollection.map(
        unidadMedidaItem => this.getUnidadMedidaIdentifier(unidadMedidaItem)!
      );
      const unidadMedidasToAdd = unidadMedidas.filter(unidadMedidaItem => {
        const unidadMedidaIdentifier = this.getUnidadMedidaIdentifier(unidadMedidaItem);
        if (unidadMedidaCollectionIdentifiers.includes(unidadMedidaIdentifier)) {
          return false;
        }
        unidadMedidaCollectionIdentifiers.push(unidadMedidaIdentifier);
        return true;
      });
      return [...unidadMedidasToAdd, ...unidadMedidaCollection];
    }
    return unidadMedidaCollection;
  }
}
