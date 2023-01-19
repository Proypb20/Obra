import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBalanceRep, NewBalanceRep } from '../balance-rep.model';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

export type PartialUpdateBalanceRep = Partial<IBalanceRep> & Pick<IBalanceRep, 'id'>;

export type EntityResponseType = HttpResponse<IBalanceRep>;
export type EntityArrayResponseType = HttpResponse<IBalanceRep[]>;

type RestOf<T extends IBalanceRep | NewBalanceRep> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestBalanceRep = RestOf<IBalanceRep>;

export type NewRestBalanceRep = RestOf<NewBalanceRep>;

export type PartialUpdateRestBalanceRep = RestOf<PartialUpdateBalanceRep>;

@Injectable({ providedIn: 'root' })
export class BalanceRepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/balance-rep');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBalanceRep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBalanceRep[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  getBalanceRepIdentifier(balanceRep: Pick<IBalanceRep, 'id'>): number {
    return balanceRep.id;
  }

  compareBalanceRep(o1: Pick<IBalanceRep, 'id'> | null, o2: Pick<IBalanceRep, 'id'> | null): boolean {
    return o1 && o2 ? this.getBalanceRepIdentifier(o1) === this.getBalanceRepIdentifier(o2) : o1 === o2;
  }

  addBalanceRepToCollectionIfMissing<Type extends Pick<IBalanceRep, 'id'>>(
    balanceRepCollection: Type[],
    ...balanceRepsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const balanceReps: Type[] = balanceRepsToCheck.filter(isPresent);
    if (balanceReps.length > 0) {
      const balanceRepCollectionIdentifiers = balanceRepCollection.map(balanceRepItem => this.getBalanceRepIdentifier(balanceRepItem)!);
      const balanceRepsToAdd = balanceReps.filter(balanceRepItem => {
        const balanceRepIdentifier = this.getBalanceRepIdentifier(balanceRepItem);
        if (balanceRepCollectionIdentifiers.includes(balanceRepIdentifier)) {
          return false;
        }
        balanceRepCollectionIdentifiers.push(balanceRepIdentifier);
        return true;
      });
      return [...balanceRepsToAdd, ...balanceRepCollection];
    }
    return balanceRepCollection;
  }

  protected convertDateFromClient<T extends IBalanceRep | NewBalanceRep | PartialUpdateBalanceRep>(balanceRep: T): RestOf<T> {
    return {
      ...balanceRep,
      date: balanceRep.date?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restBalanceRep: RestBalanceRep): IBalanceRep {
    return {
      ...restBalanceRep,
      date: restBalanceRep.date ? dayjs(restBalanceRep.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestBalanceRep>): HttpResponse<IBalanceRep> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestBalanceRep[]>): HttpResponse<IBalanceRep[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
