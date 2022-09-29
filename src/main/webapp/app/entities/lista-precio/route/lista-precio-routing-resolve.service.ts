import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IListaPrecio } from '../lista-precio.model';
import { ListaPrecioService } from '../service/lista-precio.service';

@Injectable({ providedIn: 'root' })
export class ListaPrecioRoutingResolveService implements Resolve<IListaPrecio | null> {
  constructor(protected service: ListaPrecioService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IListaPrecio | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((listaPrecio: HttpResponse<IListaPrecio>) => {
          if (listaPrecio.body) {
            return of(listaPrecio.body);
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
