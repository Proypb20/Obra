import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarea, NewTarea } from '../tarea.model';

export type PartialUpdateTarea = Partial<ITarea> & Pick<ITarea, 'id'>;

export type EntityResponseType = HttpResponse<ITarea>;
export type EntityArrayResponseType = HttpResponse<ITarea[]>;
export type EntityArrayResponseType2 = HttpResponse<ITarea[]>;

@Injectable({ providedIn: 'root' })
export class TareaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tareas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tarea: NewTarea): Observable<EntityResponseType> {
    return this.http.post<ITarea>(this.resourceUrl, tarea, { observe: 'response' });
  }

  update(tarea: ITarea): Observable<EntityResponseType> {
    return this.http.put<ITarea>(`${this.resourceUrl}/${this.getTareaIdentifier(tarea)}`, tarea, { observe: 'response' });
  }

  partialUpdate(tarea: PartialUpdateTarea): Observable<EntityResponseType> {
    return this.http.patch<ITarea>(`${this.resourceUrl}/${this.getTareaIdentifier(tarea)}`, tarea, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarea[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTareaIdentifier(tarea: Pick<ITarea, 'id'>): number {
    return tarea.id;
  }

  compareTarea(o1: Pick<ITarea, 'id'> | null, o2: Pick<ITarea, 'id'> | null): boolean {
    return o1 && o2 ? this.getTareaIdentifier(o1) === this.getTareaIdentifier(o2) : o1 === o2;
  }

  addTareaToCollectionIfMissing<Type extends Pick<ITarea, 'id'>>(
    tareaCollection: Type[],
    ...tareasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tareas: Type[] = tareasToCheck.filter(isPresent);
    if (tareas.length > 0) {
      const tareaCollectionIdentifiers = tareaCollection.map(tareaItem => this.getTareaIdentifier(tareaItem)!);
      const tareasToAdd = tareas.filter(tareaItem => {
        const tareaIdentifier = this.getTareaIdentifier(tareaItem);
        if (tareaCollectionIdentifiers.includes(tareaIdentifier)) {
          return false;
        }
        tareaCollectionIdentifiers.push(tareaIdentifier);
        return true;
      });
      return [...tareasToAdd, ...tareaCollection];
    }
    return tareaCollection;
  }
}
