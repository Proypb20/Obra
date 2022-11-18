import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaldo } from '../saldo.model';
import { SaldoService } from '../service/saldo.service';

@Injectable({ providedIn: 'root' })
export class SaldoRoutingResolveService implements Resolve<ISaldo | null> {
  constructor(protected service: SaldoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISaldo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((saldo: HttpResponse<ISaldo>) => {
          if (saldo.body) {
            return of(saldo.body);
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
