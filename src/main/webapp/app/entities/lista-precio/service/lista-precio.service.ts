import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IListaPrecio, NewListaPrecio } from '../lista-precio.model';

export type PartialUpdateListaPrecio = Partial<IListaPrecio> & Pick<IListaPrecio, 'id'>;

type RestOf<T extends IListaPrecio | NewListaPrecio> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestListaPrecio = RestOf<IListaPrecio>;

export type NewRestListaPrecio = RestOf<NewListaPrecio>;

export type PartialUpdateRestListaPrecio = RestOf<PartialUpdateListaPrecio>;

export type EntityResponseType = HttpResponse<IListaPrecio>;
export type EntityArrayResponseType = HttpResponse<IListaPrecio[]>;

@Injectable({ providedIn: 'root' })
export class ListaPrecioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lista-precios');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(listaPrecio: NewListaPrecio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(listaPrecio);
    return this.http
      .post<RestListaPrecio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(listaPrecio: IListaPrecio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(listaPrecio);
    return this.http
      .put<RestListaPrecio>(`${this.resourceUrl}/${this.getListaPrecioIdentifier(listaPrecio)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(listaPrecio: PartialUpdateListaPrecio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(listaPrecio);
    return this.http
      .patch<RestListaPrecio>(`${this.resourceUrl}/${this.getListaPrecioIdentifier(listaPrecio)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestListaPrecio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestListaPrecio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getListaPrecioIdentifier(listaPrecio: Pick<IListaPrecio, 'id'>): number {
    return listaPrecio.id;
  }

  compareListaPrecio(o1: Pick<IListaPrecio, 'id'> | null, o2: Pick<IListaPrecio, 'id'> | null): boolean {
    return o1 && o2 ? this.getListaPrecioIdentifier(o1) === this.getListaPrecioIdentifier(o2) : o1 === o2;
  }

  addListaPrecioToCollectionIfMissing<Type extends Pick<IListaPrecio, 'id'>>(
    listaPrecioCollection: Type[],
    ...listaPreciosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const listaPrecios: Type[] = listaPreciosToCheck.filter(isPresent);
    if (listaPrecios.length > 0) {
      const listaPrecioCollectionIdentifiers = listaPrecioCollection.map(
        listaPrecioItem => this.getListaPrecioIdentifier(listaPrecioItem)!
      );
      const listaPreciosToAdd = listaPrecios.filter(listaPrecioItem => {
        const listaPrecioIdentifier = this.getListaPrecioIdentifier(listaPrecioItem);
        if (listaPrecioCollectionIdentifiers.includes(listaPrecioIdentifier)) {
          return false;
        }
        listaPrecioCollectionIdentifiers.push(listaPrecioIdentifier);
        return true;
      });
      return [...listaPreciosToAdd, ...listaPrecioCollection];
    }
    return listaPrecioCollection;
  }

  submit(file: File, idProv: number): Observable<HttpResponse<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('idProv', idProv.toString());
    return this.http.post<RestListaPrecio>(`${this.resourceUrl}/importXLS`, formData, { observe: 'response' });
  }

  protected convertDateFromClient<T extends IListaPrecio | NewListaPrecio | PartialUpdateListaPrecio>(listaPrecio: T): RestOf<T> {
    return {
      ...listaPrecio,
      date: listaPrecio.date?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restListaPrecio: RestListaPrecio): IListaPrecio {
    return {
      ...restListaPrecio,
      date: restListaPrecio.date ? dayjs(restListaPrecio.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestListaPrecio>): HttpResponse<IListaPrecio> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestListaPrecio[]>): HttpResponse<IListaPrecio[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
