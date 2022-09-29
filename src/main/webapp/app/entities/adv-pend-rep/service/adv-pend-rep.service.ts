import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdvPendRep, NewAdvPendRep } from '../adv-pend-rep.model';

export type PartialUpdateAdvPendRep = Partial<IAdvPendRep> & Pick<IAdvPendRep, 'id'>;

export type EntityResponseType = HttpResponse<IAdvPendRep>;
export type EntityArrayResponseType = HttpResponse<IAdvPendRep[]>;

@Injectable({ providedIn: 'root' })
export class AdvPendRepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/adv-pending-rep');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(advPendRep: NewAdvPendRep): Observable<EntityResponseType> {
    return this.http.post<IAdvPendRep>(this.resourceUrl, advPendRep, { observe: 'response' });
  }

  update(advPendRep: IAdvPendRep): Observable<EntityResponseType> {
    return this.http.put<IAdvPendRep>(`${this.resourceUrl}/${this.getAdvPendRepIdentifier(advPendRep)}`, advPendRep, {
      observe: 'response',
    });
  }

  partialUpdate(advPendRep: PartialUpdateAdvPendRep): Observable<EntityResponseType> {
    return this.http.patch<IAdvPendRep>(`${this.resourceUrl}/${this.getAdvPendRepIdentifier(advPendRep)}`, advPendRep, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdvPendRep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdvPendRep[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAdvPendRepIdentifier(advPendRep: Pick<IAdvPendRep, 'id'>): number {
    return advPendRep.id;
  }

  compareAdvPendRep(o1: Pick<IAdvPendRep, 'id'> | null, o2: Pick<IAdvPendRep, 'id'> | null): boolean {
    return o1 && o2 ? this.getAdvPendRepIdentifier(o1) === this.getAdvPendRepIdentifier(o2) : o1 === o2;
  }

  addAdvPendRepToCollectionIfMissing<Type extends Pick<IAdvPendRep, 'id'>>(
    advPendRepCollection: Type[],
    ...advPendRepsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const advPendReps: Type[] = advPendRepsToCheck.filter(isPresent);
    if (advPendReps.length > 0) {
      const advPendRepCollectionIdentifiers = advPendRepCollection.map(advPendRepItem => this.getAdvPendRepIdentifier(advPendRepItem)!);
      const advPendRepsToAdd = advPendReps.filter(advPendRepItem => {
        const advPendRepIdentifier = this.getAdvPendRepIdentifier(advPendRepItem);
        if (advPendRepCollectionIdentifiers.includes(advPendRepIdentifier)) {
          return false;
        }
        advPendRepCollectionIdentifiers.push(advPendRepIdentifier);
        return true;
      });
      return [...advPendRepsToAdd, ...advPendRepCollection];
    }
    return advPendRepCollection;
  }
}
