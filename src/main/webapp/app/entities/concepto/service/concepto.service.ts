import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConcepto, NewConcepto } from '../concepto.model';

export type PartialUpdateConcepto = Partial<IConcepto> & Pick<IConcepto, 'id'>;

export type EntityResponseType = HttpResponse<IConcepto>;
export type EntityArrayResponseType = HttpResponse<IConcepto[]>;

@Injectable({ providedIn: 'root' })
export class ConceptoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/conceptos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(concepto: NewConcepto): Observable<EntityResponseType> {
    return this.http.post<IConcepto>(this.resourceUrl, concepto, { observe: 'response' });
  }

  update(concepto: IConcepto): Observable<EntityResponseType> {
    return this.http.put<IConcepto>(`${this.resourceUrl}/${this.getConceptoIdentifier(concepto)}`, concepto, { observe: 'response' });
  }

  partialUpdate(concepto: PartialUpdateConcepto): Observable<EntityResponseType> {
    return this.http.patch<IConcepto>(`${this.resourceUrl}/${this.getConceptoIdentifier(concepto)}`, concepto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConcepto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConcepto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getConceptoIdentifier(concepto: Pick<IConcepto, 'id'>): number {
    return concepto.id;
  }

  compareConcepto(o1: Pick<IConcepto, 'id'> | null, o2: Pick<IConcepto, 'id'> | null): boolean {
    return o1 && o2 ? this.getConceptoIdentifier(o1) === this.getConceptoIdentifier(o2) : o1 === o2;
  }

  addConceptoToCollectionIfMissing<Type extends Pick<IConcepto, 'id'>>(
    conceptoCollection: Type[],
    ...conceptosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const conceptos: Type[] = conceptosToCheck.filter(isPresent);
    if (conceptos.length > 0) {
      const conceptoCollectionIdentifiers = conceptoCollection.map(conceptoItem => this.getConceptoIdentifier(conceptoItem)!);
      const conceptosToAdd = conceptos.filter(conceptoItem => {
        const conceptoIdentifier = this.getConceptoIdentifier(conceptoItem);
        if (conceptoCollectionIdentifiers.includes(conceptoIdentifier)) {
          return false;
        }
        conceptoCollectionIdentifiers.push(conceptoIdentifier);
        return true;
      });
      return [...conceptosToAdd, ...conceptoCollection];
    }
    return conceptoCollection;
  }
}
