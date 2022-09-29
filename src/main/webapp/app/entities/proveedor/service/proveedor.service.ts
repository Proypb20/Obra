import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProveedor, NewProveedor } from '../proveedor.model';

export type PartialUpdateProveedor = Partial<IProveedor> & Pick<IProveedor, 'id'>;

export type EntityResponseType = HttpResponse<IProveedor>;
export type EntityArrayResponseType = HttpResponse<IProveedor[]>;

@Injectable({ providedIn: 'root' })
export class ProveedorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/proveedors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(proveedor: NewProveedor): Observable<EntityResponseType> {
    return this.http.post<IProveedor>(this.resourceUrl, proveedor, { observe: 'response' });
  }

  update(proveedor: IProveedor): Observable<EntityResponseType> {
    return this.http.put<IProveedor>(`${this.resourceUrl}/${this.getProveedorIdentifier(proveedor)}`, proveedor, { observe: 'response' });
  }

  partialUpdate(proveedor: PartialUpdateProveedor): Observable<EntityResponseType> {
    return this.http.patch<IProveedor>(`${this.resourceUrl}/${this.getProveedorIdentifier(proveedor)}`, proveedor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProveedor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProveedorIdentifier(proveedor: Pick<IProveedor, 'id'>): number {
    return proveedor.id;
  }

  compareProveedor(o1: Pick<IProveedor, 'id'> | null, o2: Pick<IProveedor, 'id'> | null): boolean {
    return o1 && o2 ? this.getProveedorIdentifier(o1) === this.getProveedorIdentifier(o2) : o1 === o2;
  }

  addProveedorToCollectionIfMissing<Type extends Pick<IProveedor, 'id'>>(
    proveedorCollection: Type[],
    ...proveedorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const proveedors: Type[] = proveedorsToCheck.filter(isPresent);
    if (proveedors.length > 0) {
      const proveedorCollectionIdentifiers = proveedorCollection.map(proveedorItem => this.getProveedorIdentifier(proveedorItem)!);
      const proveedorsToAdd = proveedors.filter(proveedorItem => {
        const proveedorIdentifier = this.getProveedorIdentifier(proveedorItem);
        if (proveedorCollectionIdentifiers.includes(proveedorIdentifier)) {
          return false;
        }
        proveedorCollectionIdentifiers.push(proveedorIdentifier);
        return true;
      });
      return [...proveedorsToAdd, ...proveedorCollection];
    }
    return proveedorCollection;
  }
}
