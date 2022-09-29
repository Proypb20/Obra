import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubcontratista } from '../subcontratista.model';
import { SubcontratistaService } from '../service/subcontratista.service';

@Injectable({ providedIn: 'root' })
export class SubcontratistaRoutingResolveService implements Resolve<ISubcontratista | null> {
  constructor(protected service: SubcontratistaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubcontratista | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subcontratista: HttpResponse<ISubcontratista>) => {
          if (subcontratista.body) {
            return of(subcontratista.body);
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
