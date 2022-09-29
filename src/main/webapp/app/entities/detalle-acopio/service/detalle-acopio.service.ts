import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetalleAcopio, NewDetalleAcopio } from '../detalle-acopio.model';

export type PartialUpdateDetalleAcopio = Partial<IDetalleAcopio> & Pick<IDetalleAcopio, 'id'>;

type RestOf<T extends IDetalleAcopio | NewDetalleAcopio> = Omit<T, 'date' | 'requestDate' | 'promiseDate'> & {
  date?: string | null;
  requestDate?: string | null;
  promiseDate?: string | null;
};

export type RestDetalleAcopio = RestOf<IDetalleAcopio>;

export type NewRestDetalleAcopio = RestOf<NewDetalleAcopio>;

export type PartialUpdateRestDetalleAcopio = RestOf<PartialUpdateDetalleAcopio>;

export type EntityResponseType = HttpResponse<IDetalleAcopio>;
export type EntityArrayResponseType = HttpResponse<IDetalleAcopio[]>;

@Injectable({ providedIn: 'root' })
export class DetalleAcopioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detalle-acopios');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detalleAcopio: NewDetalleAcopio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalleAcopio);
    return this.http
      .post<RestDetalleAcopio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(detalleAcopio: IDetalleAcopio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalleAcopio);
    return this.http
      .put<RestDetalleAcopio>(`${this.resourceUrl}/${this.getDetalleAcopioIdentifier(detalleAcopio)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(detalleAcopio: PartialUpdateDetalleAcopio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalleAcopio);
    return this.http
      .patch<RestDetalleAcopio>(`${this.resourceUrl}/${this.getDetalleAcopioIdentifier(detalleAcopio)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDetalleAcopio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDetalleAcopio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDetalleAcopioIdentifier(detalleAcopio: Pick<IDetalleAcopio, 'id'>): number {
    return detalleAcopio.id;
  }

  compareDetalleAcopio(o1: Pick<IDetalleAcopio, 'id'> | null, o2: Pick<IDetalleAcopio, 'id'> | null): boolean {
    return o1 && o2 ? this.getDetalleAcopioIdentifier(o1) === this.getDetalleAcopioIdentifier(o2) : o1 === o2;
  }

  addDetalleAcopioToCollectionIfMissing<Type extends Pick<IDetalleAcopio, 'id'>>(
    detalleAcopioCollection: Type[],
    ...detalleAcopiosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const detalleAcopios: Type[] = detalleAcopiosToCheck.filter(isPresent);
    if (detalleAcopios.length > 0) {
      const detalleAcopioCollectionIdentifiers = detalleAcopioCollection.map(
        detalleAcopioItem => this.getDetalleAcopioIdentifier(detalleAcopioItem)!
      );
      const detalleAcopiosToAdd = detalleAcopios.filter(detalleAcopioItem => {
        const detalleAcopioIdentifier = this.getDetalleAcopioIdentifier(detalleAcopioItem);
        if (detalleAcopioCollectionIdentifiers.includes(detalleAcopioIdentifier)) {
          return false;
        }
        detalleAcopioCollectionIdentifiers.push(detalleAcopioIdentifier);
        return true;
      });
      return [...detalleAcopiosToAdd, ...detalleAcopioCollection];
    }
    return detalleAcopioCollection;
  }

  protected convertDateFromClient<T extends IDetalleAcopio | NewDetalleAcopio | PartialUpdateDetalleAcopio>(detalleAcopio: T): RestOf<T> {
    return {
      ...detalleAcopio,
      date: detalleAcopio.date?.format(DATE_FORMAT) ?? null,
      requestDate: detalleAcopio.requestDate?.format(DATE_FORMAT) ?? null,
      promiseDate: detalleAcopio.promiseDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDetalleAcopio: RestDetalleAcopio): IDetalleAcopio {
    return {
      ...restDetalleAcopio,
      date: restDetalleAcopio.date ? dayjs(restDetalleAcopio.date) : undefined,
      requestDate: restDetalleAcopio.requestDate ? dayjs(restDetalleAcopio.requestDate) : undefined,
      promiseDate: restDetalleAcopio.promiseDate ? dayjs(restDetalleAcopio.promiseDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDetalleAcopio>): HttpResponse<IDetalleAcopio> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDetalleAcopio[]>): HttpResponse<IDetalleAcopio[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
