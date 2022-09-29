import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarea } from '../tarea.model';
import { TareaService } from '../service/tarea.service';

@Injectable({ providedIn: 'root' })
export class TareaRoutingResolveService implements Resolve<ITarea | null> {
  constructor(protected service: TareaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarea | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tarea: HttpResponse<ITarea>) => {
          if (tarea.body) {
            return of(tarea.body);
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
