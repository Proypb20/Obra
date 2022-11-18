import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISeguimientoRep } from '../seguimiento-rep.model';
import { SeguimientoRepService } from '../service/seguimiento-rep.service';

@Injectable({ providedIn: 'root' })
export class SeguimientoRepRoutingResolveService implements Resolve<ISeguimientoRep | null> {
  constructor(protected service: SeguimientoRepService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISeguimientoRep | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((seguimientoRep: HttpResponse<ISeguimientoRep>) => {
          if (seguimientoRep.body) {
            return of(seguimientoRep.body);
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
