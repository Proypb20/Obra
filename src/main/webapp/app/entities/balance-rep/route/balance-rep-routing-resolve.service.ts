import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBalanceRep } from '../balance-rep.model';
import { BalanceRepService } from '../service/balance-rep.service';

@Injectable({ providedIn: 'root' })
export class BalanceRepRoutingResolveService implements Resolve<IBalanceRep | null> {
  constructor(protected service: BalanceRepService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBalanceRep | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((balanceRep: HttpResponse<IBalanceRep>) => {
          if (balanceRep.body) {
            return of(balanceRep.body);
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
