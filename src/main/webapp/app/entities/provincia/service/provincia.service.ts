import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProvincia, NewProvincia } from '../provincia.model';

export type PartialUpdateProvincia = Partial<IProvincia> & Pick<IProvincia, 'id'>;

export type EntityResponseType = HttpResponse<IProvincia>;
export type EntityArrayResponseType = HttpResponse<IProvincia[]>;

@Injectable({ providedIn: 'root' })
export class ProvinciaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/provincias');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(provincia: NewProvincia): Observable<EntityResponseType> {
    return this.http.post<IProvincia>(this.resourceUrl, provincia, { observe: 'response' });
  }

  update(provincia: IProvincia): Observable<EntityResponseType> {
    return this.http.put<IProvincia>(`${this.resourceUrl}/${this.getProvinciaIdentifier(provincia)}`, provincia, { observe: 'response' });
  }

  partialUpdate(provincia: PartialUpdateProvincia): Observable<EntityResponseType> {
    return this.http.patch<IProvincia>(`${this.resourceUrl}/${this.getProvinciaIdentifier(provincia)}`, provincia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProvincia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProvincia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProvinciaIdentifier(provincia: Pick<IProvincia, 'id'>): number {
    return provincia.id;
  }

  compareProvincia(o1: Pick<IProvincia, 'id'> | null, o2: Pick<IProvincia, 'id'> | null): boolean {
    return o1 && o2 ? this.getProvinciaIdentifier(o1) === this.getProvinciaIdentifier(o2) : o1 === o2;
  }

  addProvinciaToCollectionIfMissing<Type extends Pick<IProvincia, 'id'>>(
    provinciaCollection: Type[],
    ...provinciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const provincias: Type[] = provinciasToCheck.filter(isPresent);
    if (provincias.length > 0) {
      const provinciaCollectionIdentifiers = provinciaCollection.map(provinciaItem => this.getProvinciaIdentifier(provinciaItem)!);
      const provinciasToAdd = provincias.filter(provinciaItem => {
        const provinciaIdentifier = this.getProvinciaIdentifier(provinciaItem);
        if (provinciaCollectionIdentifiers.includes(provinciaIdentifier)) {
          return false;
        }
        provinciaCollectionIdentifiers.push(provinciaIdentifier);
        return true;
      });
      return [...provinciasToAdd, ...provinciaCollection];
    }
    return provinciaCollection;
  }
}
