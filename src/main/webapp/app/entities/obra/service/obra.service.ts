import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IObra, NewObra } from '../obra.model';

export type PartialUpdateObra = Partial<IObra> & Pick<IObra, 'id'>;

export type EntityResponseType = HttpResponse<IObra>;
export type EntityArrayResponseType = HttpResponse<IObra[]>;

@Injectable({ providedIn: 'root' })
export class ObraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/obras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(obra: NewObra): Observable<EntityResponseType> {
    return this.http.post<IObra>(this.resourceUrl, obra, { observe: 'response' });
  }

  update(obra: IObra): Observable<EntityResponseType> {
    return this.http.put<IObra>(`${this.resourceUrl}/${this.getObraIdentifier(obra)}`, obra, { observe: 'response' });
  }

  partialUpdate(obra: PartialUpdateObra): Observable<EntityResponseType> {
    return this.http.patch<IObra>(`${this.resourceUrl}/${this.getObraIdentifier(obra)}`, obra, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IObra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IObra[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getObraIdentifier(obra: Pick<IObra, 'id'>): number {
    return obra.id;
  }

  compareObra(o1: Pick<IObra, 'id'> | null, o2: Pick<IObra, 'id'> | null): boolean {
    return o1 && o2 ? this.getObraIdentifier(o1) === this.getObraIdentifier(o2) : o1 === o2;
  }

  addObraToCollectionIfMissing<Type extends Pick<IObra, 'id'>>(
    obraCollection: Type[],
    ...obrasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const obras: Type[] = obrasToCheck.filter(isPresent);
    if (obras.length > 0) {
      const obraCollectionIdentifiers = obraCollection.map(obraItem => this.getObraIdentifier(obraItem)!);
      const obrasToAdd = obras.filter(obraItem => {
        const obraIdentifier = this.getObraIdentifier(obraItem);
        if (obraCollectionIdentifiers.includes(obraIdentifier)) {
          return false;
        }
        obraCollectionIdentifiers.push(obraIdentifier);
        return true;
      });
      return [...obrasToAdd, ...obraCollection];
    }
    return obraCollection;
  }
}
