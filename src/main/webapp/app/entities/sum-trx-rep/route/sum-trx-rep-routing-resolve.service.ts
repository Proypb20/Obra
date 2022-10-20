import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISumTrxRep } from '../sum-trx-rep.model';
import { SumTrxRepService } from '../service/sum-trx-rep.service';

@Injectable({ providedIn: 'root' })
export class SumTrxRepRoutingResolveService implements Resolve<ISumTrxRep | null> {
  constructor(protected service: SumTrxRepService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISumTrxRep | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sumTrxRep: HttpResponse<ISumTrxRep>) => {
          if (sumTrxRep.body) {
            return of(sumTrxRep.body);
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
