import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdvPendRep } from '../adv-pend-rep.model';
import { AdvPendRepService } from '../service/adv-pend-rep.service';

@Injectable({ providedIn: 'root' })
export class AdvPendRepRoutingResolveService implements Resolve<IAdvPendRep | null> {
  constructor(protected service: AdvPendRepService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdvPendRep | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((advPendRep: HttpResponse<IAdvPendRep>) => {
          if (advPendRep.body) {
            return of(advPendRep.body);
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
