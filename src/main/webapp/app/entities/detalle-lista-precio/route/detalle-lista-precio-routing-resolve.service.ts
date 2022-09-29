import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetalleListaPrecio } from '../detalle-lista-precio.model';
import { DetalleListaPrecioService } from '../service/detalle-lista-precio.service';

@Injectable({ providedIn: 'root' })
export class DetalleListaPrecioRoutingResolveService implements Resolve<IDetalleListaPrecio | null> {
  constructor(protected service: DetalleListaPrecioService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalleListaPrecio | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detalleListaPrecio: HttpResponse<IDetalleListaPrecio>) => {
          if (detalleListaPrecio.body) {
            return of(detalleListaPrecio.body);
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
