import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetalleListaPrecio, NewDetalleListaPrecio } from '../detalle-lista-precio.model';

export type PartialUpdateDetalleListaPrecio = Partial<IDetalleListaPrecio> & Pick<IDetalleListaPrecio, 'id'>;

export type EntityResponseType = HttpResponse<IDetalleListaPrecio>;
export type EntityArrayResponseType = HttpResponse<IDetalleListaPrecio[]>;

@Injectable({ providedIn: 'root' })
export class DetalleListaPrecioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detalle-lista-precios');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detalleListaPrecio: NewDetalleListaPrecio): Observable<EntityResponseType> {
    return this.http.post<IDetalleListaPrecio>(this.resourceUrl, detalleListaPrecio, { observe: 'response' });
  }

  update(detalleListaPrecio: IDetalleListaPrecio): Observable<EntityResponseType> {
    return this.http.put<IDetalleListaPrecio>(
      `${this.resourceUrl}/${this.getDetalleListaPrecioIdentifier(detalleListaPrecio)}`,
      detalleListaPrecio,
      { observe: 'response' }
    );
  }

  partialUpdate(detalleListaPrecio: PartialUpdateDetalleListaPrecio): Observable<EntityResponseType> {
    return this.http.patch<IDetalleListaPrecio>(
      `${this.resourceUrl}/${this.getDetalleListaPrecioIdentifier(detalleListaPrecio)}`,
      detalleListaPrecio,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetalleListaPrecio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetalleListaPrecio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDetalleListaPrecioIdentifier(detalleListaPrecio: Pick<IDetalleListaPrecio, 'id'>): number {
    return detalleListaPrecio.id;
  }

  compareDetalleListaPrecio(o1: Pick<IDetalleListaPrecio, 'id'> | null, o2: Pick<IDetalleListaPrecio, 'id'> | null): boolean {
    return o1 && o2 ? this.getDetalleListaPrecioIdentifier(o1) === this.getDetalleListaPrecioIdentifier(o2) : o1 === o2;
  }

  addDetalleListaPrecioToCollectionIfMissing<Type extends Pick<IDetalleListaPrecio, 'id'>>(
    detalleListaPrecioCollection: Type[],
    ...detalleListaPreciosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const detalleListaPrecios: Type[] = detalleListaPreciosToCheck.filter(isPresent);
    if (detalleListaPrecios.length > 0) {
      const detalleListaPrecioCollectionIdentifiers = detalleListaPrecioCollection.map(
        detalleListaPrecioItem => this.getDetalleListaPrecioIdentifier(detalleListaPrecioItem)!
      );
      const detalleListaPreciosToAdd = detalleListaPrecios.filter(detalleListaPrecioItem => {
        const detalleListaPrecioIdentifier = this.getDetalleListaPrecioIdentifier(detalleListaPrecioItem);
        if (detalleListaPrecioCollectionIdentifiers.includes(detalleListaPrecioIdentifier)) {
          return false;
        }
        detalleListaPrecioCollectionIdentifiers.push(detalleListaPrecioIdentifier);
        return true;
      });
      return [...detalleListaPreciosToAdd, ...detalleListaPrecioCollection];
    }
    return detalleListaPrecioCollection;
  }
}
