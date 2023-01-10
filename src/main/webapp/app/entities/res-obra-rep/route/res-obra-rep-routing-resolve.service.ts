import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResObraRep } from '../res-obra-rep.model';
import { ResObraRepService } from '../service/res-obra-rep.service';

@Injectable({ providedIn: 'root' })
export class ResObraRepRoutingResolveService implements Resolve<IResObraRep | null> {
  constructor(protected service: ResObraRepService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResObraRep | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resObraRep: HttpResponse<IResObraRep>) => {
          if (resObraRep.body) {
            return of(resObraRep.body);
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
