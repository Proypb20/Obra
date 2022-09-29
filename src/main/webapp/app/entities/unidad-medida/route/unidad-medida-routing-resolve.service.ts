import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUnidadMedida } from '../unidad-medida.model';
import { UnidadMedidaService } from '../service/unidad-medida.service';

@Injectable({ providedIn: 'root' })
export class UnidadMedidaRoutingResolveService implements Resolve<IUnidadMedida | null> {
  constructor(protected service: UnidadMedidaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnidadMedida | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((unidadMedida: HttpResponse<IUnidadMedida>) => {
          if (unidadMedida.body) {
            return of(unidadMedida.body);
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
