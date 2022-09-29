import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConcepto } from '../concepto.model';
import { ConceptoService } from '../service/concepto.service';

@Injectable({ providedIn: 'root' })
export class ConceptoRoutingResolveService implements Resolve<IConcepto | null> {
  constructor(protected service: ConceptoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConcepto | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((concepto: HttpResponse<IConcepto>) => {
          if (concepto.body) {
            return of(concepto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
