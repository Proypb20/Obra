import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoComprobante } from '../tipo-comprobante.model';
import { TipoComprobanteService } from '../service/tipo-comprobante.service';

@Injectable({ providedIn: 'root' })
export class TipoComprobanteRoutingResolveService implements Resolve<ITipoComprobante | null> {
  constructor(protected service: TipoComprobanteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoComprobante | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tipoComprobante: HttpResponse<ITipoComprobante>) => {
          if (tipoComprobante.body) {
            return of(tipoComprobante.body);
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
