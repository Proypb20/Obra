import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMovimiento, NewMovimiento } from '../movimiento.model';

export type PartialUpdateMovimiento = Partial<IMovimiento> & Pick<IMovimiento, 'id'>;

type RestOf<T extends IMovimiento | NewMovimiento> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestMovimiento = RestOf<IMovimiento>;

export type NewRestMovimiento = RestOf<NewMovimiento>;

export type PartialUpdateRestMovimiento = RestOf<PartialUpdateMovimiento>;

export type EntityResponseType = HttpResponse<IMovimiento>;
export type EntityArrayResponseType = HttpResponse<IMovimiento[]>;

@Injectable({ providedIn: 'root' })
export class MovimientoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/movimientos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(movimiento: NewMovimiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimiento);
    return this.http
      .post<RestMovimiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(movimiento: IMovimiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimiento);
    return this.http
      .put<RestMovimiento>(`${this.resourceUrl}/${this.getMovimientoIdentifier(movimiento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(movimiento: PartialUpdateMovimiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimiento);
    return this.http
      .patch<RestMovimiento>(`${this.resourceUrl}/${this.getMovimientoIdentifier(movimiento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMovimiento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMovimiento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMovimientoIdentifier(movimiento: Pick<IMovimiento, 'id'>): number {
    return movimiento.id;
  }

  compareMovimiento(o1: Pick<IMovimiento, 'id'> | null, o2: Pick<IMovimiento, 'id'> | null): boolean {
    return o1 && o2 ? this.getMovimientoIdentifier(o1) === this.getMovimientoIdentifier(o2) : o1 === o2;
  }

  addMovimientoToCollectionIfMissing<Type extends Pick<IMovimiento, 'id'>>(
    movimientoCollection: Type[],
    ...movimientosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const movimientos: Type[] = movimientosToCheck.filter(isPresent);
    if (movimientos.length > 0) {
      const movimientoCollectionIdentifiers = movimientoCollection.map(movimientoItem => this.getMovimientoIdentifier(movimientoItem)!);
      const movimientosToAdd = movimientos.filter(movimientoItem => {
        const movimientoIdentifier = this.getMovimientoIdentifier(movimientoItem);
        if (movimientoCollectionIdentifiers.includes(movimientoIdentifier)) {
          return false;
        }
        movimientoCollectionIdentifiers.push(movimientoIdentifier);
        return true;
      });
      return [...movimientosToAdd, ...movimientoCollection];
    }
    return movimientoCollection;
  }

  protected convertDateFromClient<T extends IMovimiento | NewMovimiento | PartialUpdateMovimiento>(movimiento: T): RestOf<T> {
    return {
      ...movimiento,
      date: movimiento.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restMovimiento: RestMovimiento): IMovimiento {
    return {
      ...restMovimiento,
      date: restMovimiento.date ? dayjs(restMovimiento.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMovimiento>): HttpResponse<IMovimiento> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMovimiento[]>): HttpResponse<IMovimiento[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
