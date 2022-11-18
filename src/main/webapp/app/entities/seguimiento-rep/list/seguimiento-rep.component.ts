import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, Validators } from '@angular/forms';
import { ISeguimientoRep } from '../seguimiento-rep.model';
import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, SeguimientoRepService } from '../service/seguimiento-rep.service';
import { EntityArrayResponseType as EntityArrayResponseType2, ObraService } from 'app/entities/obra/service/obra.service';
import { SortService } from 'app/shared/sort/sort.service';
import { IObra } from 'app/entities/obra/obra.model';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-seguimiento-rep',
  templateUrl: './seguimiento-rep.component.html',
})
export class SeguimientoRepComponent implements OnInit {
  seguimientoReps?: ISeguimientoRep[];
  seguimientoReps2?: ISeguimientoRep[];
  obras?: IObra[];
  isLoading = false;
  isLoadingObra = false;
  predicate = 'id';
  ascending = true;
  ob?: any;
  per?: any;
  showSeg = false;

  findForm = this.fb.group({
    obra: [null, Validators.required],
    periodName: [null, Validators.required],
  });

  constructor(
    protected seguimientoRepService: SeguimientoRepService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected obraService: ObraService,
    protected fb: FormBuilder
  ) {}
  trackId = (_index: number, item: ISeguimientoRep): number => this.seguimientoRepService.getSeguimientoRepIdentifier(item);

  trackIdObra = (_index: number, item: IObra): number => this.obraService.getObraIdentifier(item);

  ngOnInit(): void {
    this.load();
    this.showSeg = false;
  }

  showSeguimiento(): void {
    if (this.showSeg == false) {
      this.showSeg = true;
    } else {
      this.showSeg = false;
    }
  }

  load(): void {
    this.loadFromBackendWithRouteInformationsObra().subscribe({
      next: (res: EntityArrayResponseType2) => {
        this.onResponseSuccessObra(res);
      },
    });
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

  onChangeObra(): void {
    this.ob = this.findForm.get('obra')!.value!;
    this.findForm.patchValue({ periodName: null });
    this.per = this.findForm.get('periodName')!.value!;
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  find(): void {
    if (this.showSeg == false) {
      this.showSeguimiento();
    }
    this.ob = this.findForm.get('obra')!.value!;
    this.per = this.findForm.get('periodName')!.value!;
    this.loadFromBackendWithRouteInformations2().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess2(res);
        if (this.showSeg == false) {
          this.showSeguimiento();
        }
      },
    });
  }

  exportXLS(): void {
    const queryObject = {
      'obraName.equals': this.ob!,
      'periodName.equals': this.per!,
    };
    this.seguimientoRepService.generateXLS(queryObject).subscribe((data: any) => {
      saveAs(data, 'Seguimiento_Obra_' + this.ob! + '.xls');
    });
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected loadFromBackendWithRouteInformations2(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend2(this.predicate, this.ascending))
    );
  }

  protected loadFromBackendWithRouteInformationsObra(): Observable<EntityArrayResponseType2> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendObra(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.seguimientoReps = this.refineData(dataFromBody);
    this.seguimientoReps2 = this.seguimientoReps;
  }

  protected onResponseSuccess2(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.seguimientoReps = this.refineData(dataFromBody);
  }

  protected onResponseSuccessObra(response: EntityArrayResponseType2): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyObra(response.body);
    this.obras = this.refineDataObra(dataFromBody);
  }

  protected refineData(data: ISeguimientoRep[]): ISeguimientoRep[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataObra(data: IObra[]): IObra[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: ISeguimientoRep[] | null): ISeguimientoRep[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodyObra(data: IObra[] | null): IObra[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'obraName.equals': this.ob!,
      'periodName.equals': this.per!,
    };
    return this.seguimientoRepService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected queryBackend2(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'obraName.equals': this.ob!,
      'periodName.equals': this.per!,
    };
    return this.seguimientoRepService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected queryBackendObra(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType2> {
    this.isLoadingObra = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.obraService.query(queryObject).pipe(tap(() => (this.isLoadingObra = false)));
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
