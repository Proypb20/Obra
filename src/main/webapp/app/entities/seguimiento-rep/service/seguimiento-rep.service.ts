import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISeguimientoRep, NewSeguimientoRep } from '../seguimiento-rep.model';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

export type PartialUpdateSeguimientoRep = Partial<ISeguimientoRep> & Pick<ISeguimientoRep, 'id'>;

export type EntityResponseType = HttpResponse<ISeguimientoRep>;
export type EntityArrayResponseType = HttpResponse<ISeguimientoRep[]>;

type RestOf<T extends ISeguimientoRep | NewSeguimientoRep> = Omit<T, 'type'> & {
  type?: string | null;
};

export type RestSeguimientoRep = RestOf<ISeguimientoRep>;

export type NewRestSeguimientoRepn = RestOf<NewSeguimientoRep>;

export type PartialUpdateRestSeguimientoRep = RestOf<PartialUpdateSeguimientoRep>;

@Injectable({ providedIn: 'root' })
export class SeguimientoRepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/seguimiento-rep');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISeguimientoRep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISeguimientoRep[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  generateXLS(req?: any): any {
    const options = createRequestOption(req);
    return this.http.get(`${this.resourceUrl}/exportXML`, { params: options, responseType: 'blob' });
  }

  getSeguimientoRepIdentifier(seguimientoRep: Pick<ISeguimientoRep, 'id'>): number {
    return seguimientoRep.id;
  }

  compareSeguimientoRep(o1: Pick<ISeguimientoRep, 'id'> | null, o2: Pick<ISeguimientoRep, 'id'> | null): boolean {
    return o1 && o2 ? this.getSeguimientoRepIdentifier(o1) === this.getSeguimientoRepIdentifier(o2) : o1 === o2;
  }

  addSeguimientoRepToCollectionIfMissing<Type extends Pick<ISeguimientoRep, 'id'>>(
    seguimientoRepCollection: Type[],
    ...seguimientoRepsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const seguimientoReps: Type[] = seguimientoRepsToCheck.filter(isPresent);
    if (seguimientoReps.length > 0) {
      const seguimientoRepCollectionIdentifiers = seguimientoRepCollection.map(
        seguimientoRepItem => this.getSeguimientoRepIdentifier(seguimientoRepItem)!
      );
      const seguimientoRepsToAdd = seguimientoReps.filter(seguimientoRepItem => {
        const seguimientoRepIdentifier = this.getSeguimientoRepIdentifier(seguimientoRepItem);
        if (seguimientoRepCollectionIdentifiers.includes(seguimientoRepIdentifier)) {
          return false;
        }
        seguimientoRepCollectionIdentifiers.push(seguimientoRepIdentifier);
        return true;
      });
      return [...seguimientoRepsToAdd, ...seguimientoRepCollection];
    }
    return seguimientoRepCollection;
  }

  protected convertDateFromClient<T extends ISeguimientoRep | NewSeguimientoRep | PartialUpdateSeguimientoRep>(
    seguimientoRep: T
  ): RestOf<T> {
    return {
      ...seguimientoRep,
    };
  }

  protected convertDateFromServer(restSeguimientoRep: RestSeguimientoRep): ISeguimientoRep {
    return {
      ...restSeguimientoRep,
      date: restSeguimientoRep.date ? dayjs(restSeguimientoRep.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSeguimientoRep>): HttpResponse<ISeguimientoRep> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSeguimientoRep[]>): HttpResponse<ISeguimientoRep[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
