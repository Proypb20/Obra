import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdvPendRep } from '../adv-pend-rep.model';
import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, AdvPendRepService } from '../service/adv-pend-rep.service';
import { EntityArrayResponseType2, TareaService } from 'app/entities/tarea/service/tarea.service';
import { EntityArrayResponseType as EntityArrayResponseType3, ObraService } from 'app/entities/obra/service/obra.service';
import {
  EntityArrayResponseType as EntityArrayResponseType4,
  SubcontratistaService,
} from 'app/entities/subcontratista/service/subcontratista.service';
import { IObra } from 'app/entities/obra/obra.model';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SortService } from 'app/shared/sort/sort.service';
import { FormBuilder, Validators } from '@angular/forms';

import { ITarea } from 'app/entities/tarea/tarea.model';

@Component({
  selector: 'jhi-adv-pend-rep',
  templateUrl: './adv-pend-rep.component.html',
})
export class AdvPendRepComponent implements OnInit {
  advPendReps?: IAdvPendRep[];
  tareas?: ITarea[];
  obras?: IObra[];
  subcontratistas?: ISubcontratista[];
  isLoading = false;
  isLoadingTarea = false;
  isLoadingObra = false;
  isLoadingSubcontratista = false;
  showDet = false;
  predicate = 'id';
  ascending = true;
  ChildOId = 0;
  ChildSId = 0;
  showFilter = false;
  obra: any;
  subco: any;

  findForm = this.fb.group({
    obra: [null, Validators.required],
    subcontratista: [null, Validators.required],
  });

  constructor(
    protected advPendRepService: AdvPendRepService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected tareaService: TareaService,
    protected obraService: ObraService,
    protected subcontratistaService: SubcontratistaService,
    protected fb: FormBuilder
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
    this.loadFromBackendWithRouteInformationsObra().subscribe({
      next: (res: EntityArrayResponseType3) => {
        this.onResponseSuccessObra(res);
      },
    });
    this.loadFromBackendWithRouteInformationsSubcontratista().subscribe({
      next: (res: EntityArrayResponseType4) => {
        this.onResponseSuccessSubcontratista(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  Cancel(): void {
    window.history.back();
  }

  onChangeObra(): void {
    if (this.findForm.get('obra')!.value! == null) {
      this.ChildOId = 0;
    } else {
      this.obra = this.findForm.get('obra')!.value!;
      this.ChildOId = this.obra!.id;
    }
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  onChangeSubcontratista(): void {
    if (this.findForm.get('subcontratista')!.value! == null) {
      this.ChildSId = 0;
    } else {
      this.subco = this.findForm.get('subcontratista')!.value!;
      this.ChildSId = this.subco!.id;
    }

    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  showFilters(): void {
    if (this.showFilter) {
      this.showFilter = false;
      this.ChildOId = 0;
      this.ChildSId = 0;
      this.loadFromBackendWithRouteInformations().subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
    } else {
      this.showFilter = true;
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

  protected loadFromBackendWithRouteInformationsObra(): Observable<EntityArrayResponseType3> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendObra(this.predicate, this.ascending))
    );
  }

  protected loadFromBackendWithRouteInformationsSubcontratista(): Observable<EntityArrayResponseType4> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendSubcontratista(this.predicate, this.ascending))
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

  protected onResponseSuccessObra(response: EntityArrayResponseType3): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyObra(response.body);
    this.obras = this.refineDataObra(dataFromBody);
  }

  protected onResponseSuccessSubcontratista(response: EntityArrayResponseType4): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodySubcontratista(response.body);
    this.subcontratistas = this.refineDataSubcontratista(dataFromBody);
  }

  protected refineData(data: IAdvPendRep[]): IAdvPendRep[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataTarea(data: ITarea[]): ITarea[] {
    return data.sort(this.sortService.startSort('tarea', this.ascending ? 1 : -1));
  }

  protected refineDataObra(data: IObra[]): IObra[] {
    return data.sort(this.sortService.startSort('name', this.ascending ? 1 : -1));
  }

  protected refineDataSubcontratista(data: ISubcontratista[]): ISubcontratista[] {
    return data.sort(this.sortService.startSort('lastName', this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IAdvPendRep[] | null): IAdvPendRep[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodyTarea(data: ITarea[] | null): ITarea[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodyObra(data: IObra[] | null): IObra[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodySubcontratista(data: ISubcontratista[] | null): ISubcontratista[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'obraId.equals': this.ChildOId,
      'subcontratistaId.equals': this.ChildSId,
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

  protected queryBackendObra(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType3> {
    this.isLoadingObra = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.obraService.query(queryObject).pipe(tap(() => (this.isLoadingObra = false)));
  }

  protected queryBackendSubcontratista(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType4> {
    this.isLoadingSubcontratista = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.subcontratistaService.query(queryObject).pipe(tap(() => (this.isLoadingSubcontratista = false)));
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
