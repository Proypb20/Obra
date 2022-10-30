import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DATE_FORMAT } from 'app/config/input.constants';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdvObraRep, NewAdvObraRep } from '../adv-obra-rep.model';

export type PartialUpdateAdvObraRep = Partial<IAdvObraRep> & Pick<IAdvObraRep, 'id'>;

export type EntityResponseType = HttpResponse<IAdvObraRep>;
export type EntityArrayResponseType = HttpResponse<IAdvObraRep[]>;

type RestOf<T extends IAdvObraRep | NewAdvObraRep> = Omit<T, 'concepto'> & {
  concepto?: string | null;
};

export type RestAdvObraRep = RestOf<IAdvObraRep>;

export type NewRestAdvObraRepn = RestOf<NewAdvObraRep>;

export type PartialUpdateRestAdvObraRep = RestOf<PartialUpdateAdvObraRep>;

@Injectable({ providedIn: 'root' })
export class AdvObraRepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/adv-obra-rep');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdvObraRep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdvObraRep[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  generateXLS(id: number): any {
    return this.http.get(`${this.resourceUrl}/generateFile/${id}`, { responseType: 'blob' });
  }

  getAdvObraRepIdentifier(advObraRep: Pick<IAdvObraRep, 'id'>): number {
    return advObraRep.id;
  }

  compareAdvObraRep(o1: Pick<IAdvObraRep, 'id'> | null, o2: Pick<IAdvObraRep, 'id'> | null): boolean {
    return o1 && o2 ? this.getAdvObraRepIdentifier(o1) === this.getAdvObraRepIdentifier(o2) : o1 === o2;
  }

  addAdvObraRepToCollectionIfMissing<Type extends Pick<IAdvObraRep, 'id'>>(
    advObraRepCollection: Type[],
    ...advObraRepsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const advObraReps: Type[] = advObraRepsToCheck.filter(isPresent);
    if (advObraReps.length > 0) {
      const advObraRepCollectionIdentifiers = advObraRepCollection.map(advObraRepItem => this.getAdvObraRepIdentifier(advObraRepItem)!);
      const advObraRepsToAdd = advObraReps.filter(advObraRepItem => {
        const advObraRepIdentifier = this.getAdvObraRepIdentifier(advObraRepItem);
        if (advObraRepCollectionIdentifiers.includes(advObraRepIdentifier)) {
          return false;
        }
        advObraRepCollectionIdentifiers.push(advObraRepIdentifier);
        return true;
      });
      return [...advObraRepsToAdd, ...advObraRepCollection];
    }
    return advObraRepCollection;
  }

  protected convertDateFromClient<T extends IAdvObraRep | NewAdvObraRep | PartialUpdateAdvObraRep>(advObraRep: T): RestOf<T> {
    return {
      ...advObraRep,
    };
  }
}
