import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IObra } from '../obra.model';
import { ObraService } from '../service/obra.service';

@Injectable({ providedIn: 'root' })
export class ObraRoutingResolveService implements Resolve<IObra | null> {
  constructor(protected service: ObraService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObra | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((obra: HttpResponse<IObra>) => {
          if (obra.body) {
            return of(obra.body);
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
