import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMovimiento } from '../movimiento.model';
import { MovimientoService } from '../service/movimiento.service';

@Injectable({ providedIn: 'root' })
export class MovimientoRoutingResolveService implements Resolve<IMovimiento | null> {
  constructor(protected service: MovimientoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMovimiento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((movimiento: HttpResponse<IMovimiento>) => {
          if (movimiento.body) {
            return of(movimiento.body);
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
