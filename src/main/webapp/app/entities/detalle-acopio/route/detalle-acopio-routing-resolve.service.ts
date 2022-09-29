import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetalleAcopio } from '../detalle-acopio.model';
import { DetalleAcopioService } from '../service/detalle-acopio.service';

@Injectable({ providedIn: 'root' })
export class DetalleAcopioRoutingResolveService implements Resolve<IDetalleAcopio | null> {
  constructor(protected service: DetalleAcopioService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalleAcopio | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detalleAcopio: HttpResponse<IDetalleAcopio>) => {
          if (detalleAcopio.body) {
            return of(detalleAcopio.body);
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
