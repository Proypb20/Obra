import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdvObraRep } from '../adv-obra-rep.model';
import { AdvObraRepService } from '../service/adv-obra-rep.service';

@Injectable({ providedIn: 'root' })
export class AdvObraRepRoutingResolveService implements Resolve<IAdvObraRep | null> {
  constructor(protected service: AdvObraRepService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdvObraRep | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((advObraRep: HttpResponse<IAdvObraRep>) => {
          if (advObraRep.body) {
            return of(advObraRep.body);
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
