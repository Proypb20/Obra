import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubcontratista, NewSubcontratista } from '../subcontratista.model';

export type PartialUpdateSubcontratista = Partial<ISubcontratista> & Pick<ISubcontratista, 'id'>;

export type EntityResponseType = HttpResponse<ISubcontratista>;
export type EntityArrayResponseType = HttpResponse<ISubcontratista[]>;

@Injectable({ providedIn: 'root' })
export class SubcontratistaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subcontratistas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subcontratista: NewSubcontratista): Observable<EntityResponseType> {
    return this.http.post<ISubcontratista>(this.resourceUrl, subcontratista, { observe: 'response' });
  }

  update(subcontratista: ISubcontratista): Observable<EntityResponseType> {
    return this.http.put<ISubcontratista>(`${this.resourceUrl}/${this.getSubcontratistaIdentifier(subcontratista)}`, subcontratista, {
      observe: 'response',
    });
  }

  partialUpdate(subcontratista: PartialUpdateSubcontratista): Observable<EntityResponseType> {
    return this.http.patch<ISubcontratista>(`${this.resourceUrl}/${this.getSubcontratistaIdentifier(subcontratista)}`, subcontratista, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubcontratista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubcontratista[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSubcontratistaIdentifier(subcontratista: Pick<ISubcontratista, 'id'>): number {
    return subcontratista.id;
  }

  compareSubcontratista(o1: Pick<ISubcontratista, 'id'> | null, o2: Pick<ISubcontratista, 'id'> | null): boolean {
    return o1 && o2 ? this.getSubcontratistaIdentifier(o1) === this.getSubcontratistaIdentifier(o2) : o1 === o2;
  }

  addSubcontratistaToCollectionIfMissing<Type extends Pick<ISubcontratista, 'id'>>(
    subcontratistaCollection: Type[],
    ...subcontratistasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const subcontratistas: Type[] = subcontratistasToCheck.filter(isPresent);
    if (subcontratistas.length > 0) {
      const subcontratistaCollectionIdentifiers = subcontratistaCollection.map(
        subcontratistaItem => this.getSubcontratistaIdentifier(subcontratistaItem)!
      );
      const subcontratistasToAdd = subcontratistas.filter(subcontratistaItem => {
        const subcontratistaIdentifier = this.getSubcontratistaIdentifier(subcontratistaItem);
        if (subcontratistaCollectionIdentifiers.includes(subcontratistaIdentifier)) {
          return false;
        }
        subcontratistaCollectionIdentifiers.push(subcontratistaIdentifier);
        return true;
      });
      return [...subcontratistasToAdd, ...subcontratistaCollection];
    }
    return subcontratistaCollection;
  }
}
