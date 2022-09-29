import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProvincia } from '../provincia.model';
import { ProvinciaService } from '../service/provincia.service';

@Injectable({ providedIn: 'root' })
export class ProvinciaRoutingResolveService implements Resolve<IProvincia | null> {
  constructor(protected service: ProvinciaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProvincia | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((provincia: HttpResponse<IProvincia>) => {
          if (provincia.body) {
            return of(provincia.body);
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
