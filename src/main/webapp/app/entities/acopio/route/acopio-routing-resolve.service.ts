import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAcopio } from '../acopio.model';
import { AcopioService } from '../service/acopio.service';

@Injectable({ providedIn: 'root' })
export class AcopioRoutingResolveService implements Resolve<IAcopio | null> {
  constructor(protected service: AcopioService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcopio | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((acopio: HttpResponse<IAcopio>) => {
          if (acopio.body) {
            return of(acopio.body);
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
