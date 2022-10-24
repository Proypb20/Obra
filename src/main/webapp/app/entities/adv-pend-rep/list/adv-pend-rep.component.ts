import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdvPendRep } from '../adv-pend-rep.model';
import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, AdvPendRepService } from '../service/adv-pend-rep.service';
import { EntityArrayResponseType2, TareaService } from 'app/entities/tarea/service/tarea.service';
import { SortService } from 'app/shared/sort/sort.service';

import { ITarea } from 'app/entities/tarea/tarea.model';

@Component({
  selector: 'jhi-adv-pend-rep',
  templateUrl: './adv-pend-rep.component.html',
})
export class AdvPendRepComponent implements OnInit {
  advPendReps?: IAdvPendRep[];
  tareas?: ITarea[];
  isLoading = false;
  isLoadingTarea = false;
  showDet = false;
  predicate = 'id';
  ascending = true;
  ChildOId = 0;
  ChildSId = 0;

  constructor(
    protected advPendRepService: AdvPendRepService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected tareaService: TareaService
  ) {}
  trackId = (_index: number, item: IAdvPendRep): number => this.advPendRepService.getAdvPendRepIdentifier(item);

  trackIdTarea = (_index: number, item: ITarea): number => this.tareaService.getTareaIdentifier(item);

  ngOnInit(): void {
    this.load();
  }
  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  Cancel(): void {
    window.history.back();
  }

  showDetails(oId: number, sId: number): void {
    if (this.showDet) {
      this.showDet = false;
      this.ChildOId = 0;
      this.ChildSId = 0;
    } else {
      this.showDet = true;
      this.ChildOId = oId;
      this.ChildSId = sId;
      this.loadFromBackendWithRouteInformationsTarea().subscribe({
        next: (res: EntityArrayResponseType2) => {
          this.onResponseSuccessTarea(res);
        },
      });
    }
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected loadFromBackendWithRouteInformationsTarea(): Observable<EntityArrayResponseType2> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendTarea(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.advPendReps = this.refineData(dataFromBody);
  }

  protected onResponseSuccessTarea(response: EntityArrayResponseType2): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyTarea(response.body);
    this.tareas = this.refineDataTarea(dataFromBody);
  }

  protected refineData(data: IAdvPendRep[]): IAdvPendRep[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataTarea(data: ITarea[]): ITarea[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IAdvPendRep[] | null): IAdvPendRep[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodyTarea(data: ITarea[] | null): ITarea[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.advPendRepService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected queryBackendTarea(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType2> {
    this.isLoadingTarea = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'obraId.equals': this.ChildOId,
      'subcontratistaId.equals': this.ChildSId,
    };
    return this.tareaService.query(queryObject).pipe(tap(() => (this.isLoadingTarea = false)));
  }

  protected handleNavigation(predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }
}
