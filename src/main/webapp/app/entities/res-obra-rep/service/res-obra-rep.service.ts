import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResObraRep, NewResObraRep } from '../res-obra-rep.model';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

export type PartialUpdateResObraRep = Partial<IResObraRep> & Pick<IResObraRep, 'id'>;

export type EntityResponseType = HttpResponse<IResObraRep>;
export type EntityArrayResponseType = HttpResponse<IResObraRep[]>;

type RestOf<T extends IResObraRep | NewResObraRep> = Omit<T, 'type'> & {
  type?: string | null;
};

export type RestResObraRep = RestOf<IResObraRep>;

export type NewRestResObraRepn = RestOf<NewResObraRep>;

export type PartialUpdateRestResObraRep = RestOf<PartialUpdateResObraRep>;

@Injectable({ providedIn: 'root' })
export class ResObraRepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/res-obra-rep');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResObraRep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResObraRep[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  generateXLS(req?: any): any {
    const options = createRequestOption(req);
    return this.http.get(`${this.resourceUrl}/exportXML`, { params: options, responseType: 'blob' });
  }

  getResObraRepIdentifier(resObraRep: Pick<IResObraRep, 'id'>): number {
    return resObraRep.id;
  }

  compareResObraRep(o1: Pick<IResObraRep, 'id'> | null, o2: Pick<IResObraRep, 'id'> | null): boolean {
    return o1 && o2 ? this.getResObraRepIdentifier(o1) === this.getResObraRepIdentifier(o2) : o1 === o2;
  }

  addResObraRepToCollectionIfMissing<Type extends Pick<IResObraRep, 'id'>>(
    resObraRepCollection: Type[],
    ...resObraRepsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const resObraReps: Type[] = resObraRepsToCheck.filter(isPresent);
    if (resObraReps.length > 0) {
      const resObraRepCollectionIdentifiers = resObraRepCollection.map(resObraRepItem => this.getResObraRepIdentifier(resObraRepItem)!);
      const resObraRepsToAdd = resObraReps.filter(resObraRepItem => {
        const resObraRepIdentifier = this.getResObraRepIdentifier(resObraRepItem);
        if (resObraRepCollectionIdentifiers.includes(resObraRepIdentifier)) {
          return false;
        }
        resObraRepCollectionIdentifiers.push(resObraRepIdentifier);
        return true;
      });
      return [...resObraRepsToAdd, ...resObraRepCollection];
    }
    return resObraRepCollection;
  }

  protected convertDateFromClient<T extends IResObraRep | NewResObraRep | PartialUpdateResObraRep>(resObraRep: T): RestOf<T> {
    return {
      ...resObraRep,
    };
  }

  protected convertDateFromServer(restResObraRep: RestResObraRep): IResObraRep {
    return {
      ...restResObraRep,
      date: restResObraRep.date ? dayjs(restResObraRep.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResObraRep>): HttpResponse<IResObraRep> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResObraRep[]>): HttpResponse<IResObraRep[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
