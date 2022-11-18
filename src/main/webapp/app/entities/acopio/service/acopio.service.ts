import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAcopio, NewAcopio } from '../acopio.model';

export type PartialUpdateAcopio = Partial<IAcopio> & Pick<IAcopio, 'id'>;

type RestOf<T extends IAcopio | NewAcopio> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestAcopio = RestOf<IAcopio>;

export type NewRestAcopio = RestOf<NewAcopio>;

export type PartialUpdateRestAcopio = RestOf<PartialUpdateAcopio>;

export type EntityResponseType = HttpResponse<IAcopio>;
export type EntityArrayResponseType = HttpResponse<IAcopio[]>;

@Injectable({ providedIn: 'root' })
export class AcopioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/acopios');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(acopio: NewAcopio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acopio);
    return this.http
      .post<RestAcopio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(acopio: IAcopio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acopio);
    return this.http
      .put<RestAcopio>(`${this.resourceUrl}/${this.getAcopioIdentifier(acopio)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(acopio: PartialUpdateAcopio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acopio);
    return this.http
      .patch<RestAcopio>(`${this.resourceUrl}/${this.getAcopioIdentifier(acopio)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAcopio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAcopio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  generateXLS(id: number): any {
    return this.http.get(`${this.resourceUrl}/generateXLS/${id}`, { responseType: 'blob' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAcopioIdentifier(acopio: Pick<IAcopio, 'id'>): number {
    return acopio.id;
  }

  compareAcopio(o1: Pick<IAcopio, 'id'> | null, o2: Pick<IAcopio, 'id'> | null): boolean {
    return o1 && o2 ? this.getAcopioIdentifier(o1) === this.getAcopioIdentifier(o2) : o1 === o2;
  }

  addAcopioToCollectionIfMissing<Type extends Pick<IAcopio, 'id'>>(
    acopioCollection: Type[],
    ...acopiosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const acopios: Type[] = acopiosToCheck.filter(isPresent);
    if (acopios.length > 0) {
      const acopioCollectionIdentifiers = acopioCollection.map(acopioItem => this.getAcopioIdentifier(acopioItem)!);
      const acopiosToAdd = acopios.filter(acopioItem => {
        const acopioIdentifier = this.getAcopioIdentifier(acopioItem);
        if (acopioCollectionIdentifiers.includes(acopioIdentifier)) {
          return false;
        }
        acopioCollectionIdentifiers.push(acopioIdentifier);
        return true;
      });
      return [...acopiosToAdd, ...acopioCollection];
    }
    return acopioCollection;
  }

  protected convertDateFromClient<T extends IAcopio | NewAcopio | PartialUpdateAcopio>(acopio: T): RestOf<T> {
    return {
      ...acopio,
      date: acopio.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAcopio: RestAcopio): IAcopio {
    return {
      ...restAcopio,
      date: restAcopio.date ? dayjs(restAcopio.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAcopio>): HttpResponse<IAcopio> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAcopio[]>): HttpResponse<IAcopio[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
