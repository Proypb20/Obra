import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DATE_FORMAT } from 'app/config/input.constants';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISumTrxRep, NewSumTrxRep } from '../sum-trx-rep.model';

export type PartialUpdateSumTrxRep = Partial<ISumTrxRep> & Pick<ISumTrxRep, 'id'>;

export type EntityResponseType = HttpResponse<ISumTrxRep>;
export type EntityArrayResponseType = HttpResponse<ISumTrxRep[]>;

type RestOf<T extends ISumTrxRep | NewSumTrxRep> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestSumTrxRep = RestOf<ISumTrxRep>;

export type NewRestSumTrxRepn = RestOf<NewSumTrxRep>;

export type PartialUpdateRestSumTrxRep = RestOf<PartialUpdateSumTrxRep>;

@Injectable({ providedIn: 'root' })
export class SumTrxRepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sum-trx-rep');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISumTrxRep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISumTrxRep[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getSumTrxRepIdentifier(sumTrxRep: Pick<ISumTrxRep, 'id'>): number {
    return sumTrxRep.id;
  }

  compareSumTrxRep(o1: Pick<ISumTrxRep, 'id'> | null, o2: Pick<ISumTrxRep, 'id'> | null): boolean {
    return o1 && o2 ? this.getSumTrxRepIdentifier(o1) === this.getSumTrxRepIdentifier(o2) : o1 === o2;
  }

  addSumTrxRepToCollectionIfMissing<Type extends Pick<ISumTrxRep, 'id'>>(
    sumTrxRepCollection: Type[],
    ...sumTrxRepsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sumTrxReps: Type[] = sumTrxRepsToCheck.filter(isPresent);
    if (sumTrxReps.length > 0) {
      const sumTrxRepCollectionIdentifiers = sumTrxRepCollection.map(sumTrxRepItem => this.getSumTrxRepIdentifier(sumTrxRepItem)!);
      const sumTrxRepsToAdd = sumTrxReps.filter(sumTrxRepItem => {
        const sumTrxRepIdentifier = this.getSumTrxRepIdentifier(sumTrxRepItem);
        if (sumTrxRepCollectionIdentifiers.includes(sumTrxRepIdentifier)) {
          return false;
        }
        sumTrxRepCollectionIdentifiers.push(sumTrxRepIdentifier);
        return true;
      });
      return [...sumTrxRepsToAdd, ...sumTrxRepCollection];
    }
    return sumTrxRepCollection;
  }

  protected convertDateFromClient<T extends ISumTrxRep | NewSumTrxRep | PartialUpdateSumTrxRep>(sumTrxRep: T): RestOf<T> {
    return {
      ...sumTrxRep,
      fecha: sumTrxRep.fecha?.format(DATE_FORMAT) ?? null,
    };
  }
}
