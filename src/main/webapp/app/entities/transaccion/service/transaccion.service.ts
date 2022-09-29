import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransaccion, NewTransaccion } from '../transaccion.model';

export type PartialUpdateTransaccion = Partial<ITransaccion> & Pick<ITransaccion, 'id'>;

type RestOf<T extends ITransaccion | NewTransaccion> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestTransaccion = RestOf<ITransaccion>;

export type NewRestTransaccion = RestOf<NewTransaccion>;

export type PartialUpdateRestTransaccion = RestOf<PartialUpdateTransaccion>;

export type EntityResponseType = HttpResponse<ITransaccion>;
export type EntityArrayResponseType = HttpResponse<ITransaccion[]>;

@Injectable({ providedIn: 'root' })
export class TransaccionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transaccions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transaccion: NewTransaccion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transaccion);
    return this.http
      .post<RestTransaccion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(transaccion: ITransaccion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transaccion);
    return this.http
      .put<RestTransaccion>(`${this.resourceUrl}/${this.getTransaccionIdentifier(transaccion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(transaccion: PartialUpdateTransaccion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transaccion);
    return this.http
      .patch<RestTransaccion>(`${this.resourceUrl}/${this.getTransaccionIdentifier(transaccion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTransaccion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTransaccion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTransaccionIdentifier(transaccion: Pick<ITransaccion, 'id'>): number {
    return transaccion.id;
  }

  compareTransaccion(o1: Pick<ITransaccion, 'id'> | null, o2: Pick<ITransaccion, 'id'> | null): boolean {
    return o1 && o2 ? this.getTransaccionIdentifier(o1) === this.getTransaccionIdentifier(o2) : o1 === o2;
  }

  addTransaccionToCollectionIfMissing<Type extends Pick<ITransaccion, 'id'>>(
    transaccionCollection: Type[],
    ...transaccionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const transaccions: Type[] = transaccionsToCheck.filter(isPresent);
    if (transaccions.length > 0) {
      const transaccionCollectionIdentifiers = transaccionCollection.map(
        transaccionItem => this.getTransaccionIdentifier(transaccionItem)!
      );
      const transaccionsToAdd = transaccions.filter(transaccionItem => {
        const transaccionIdentifier = this.getTransaccionIdentifier(transaccionItem);
        if (transaccionCollectionIdentifiers.includes(transaccionIdentifier)) {
          return false;
        }
        transaccionCollectionIdentifiers.push(transaccionIdentifier);
        return true;
      });
      return [...transaccionsToAdd, ...transaccionCollection];
    }
    return transaccionCollection;
  }

  protected convertDateFromClient<T extends ITransaccion | NewTransaccion | PartialUpdateTransaccion>(transaccion: T): RestOf<T> {
    return {
      ...transaccion,
      date: transaccion.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restTransaccion: RestTransaccion): ITransaccion {
    return {
      ...restTransaccion,
      date: restTransaccion.date ? dayjs(restTransaccion.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTransaccion>): HttpResponse<ITransaccion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTransaccion[]>): HttpResponse<ITransaccion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
