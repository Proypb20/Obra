import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarea } from '../tarea.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, TareaService } from '../service/tarea.service';

import { EntityArrayResponseType as EntityArrayResponseType3, ObraService } from 'app/entities/obra/service/obra.service';
import {
  EntityArrayResponseType as EntityArrayResponseType4,
  SubcontratistaService,
} from 'app/entities/subcontratista/service/subcontratista.service';
import { EntityArrayResponseType as EntityArrayResponseType5, ConceptoService } from 'app/entities/concepto/service/concepto.service';

import { TareaDeleteDialogComponent } from '../delete/tarea-delete-dialog.component';
import { TareaSubmitDialogComponent } from '../submit/tarea-submit-dialog.component';
import { SortService } from 'app/shared/sort/sort.service';
import { saveAs } from 'file-saver';
import { IObra } from 'app/entities/obra/obra.model';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-tarea',
  templateUrl: './tarea.component.html',
})
export class TareaComponent implements OnInit {
  tareas?: ITarea[];
  isLoading = false;

  predicate = 'id';
  ascending = true;
  oId = 0;
  sId = 0;
  Id = 0;

  showFilter = false;
  filterId = 0;
  filterOId = 0;
  filterSId = 0;
  filterCId = 0;

  obra: any;
  subco: any;
  concepto: any;

  obras?: IObra[];
  subcontratistas?: ISubcontratista[];
  conceptos?: IConcepto[];

  isLoadingObra = false;
  isLoadingSubcontratista = false;
  isLoadingConcepto = false;

  findForm = this.fb.group({
    obra: [null, Validators.required],
    subcontratista: [null, Validators.required],
    concepto: [null],
  });

  constructor(
    protected tareaService: TareaService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected fb: FormBuilder,
    protected obraService: ObraService,
    protected subcontratistaService: SubcontratistaService,
    protected conceptoService: ConceptoService
  ) {}

  trackId = (_index: number, item: ITarea): number => this.tareaService.getTareaIdentifier(item);

  ngOnInit(): void {
    this.oId = history.state?.oId;
    this.sId = history.state?.sId;
    this.filterOId = this.oId;
    this.filterSId = this.sId;
    this.load();
  }

  delete(tarea: ITarea): void {
    const modalRef = this.modalService.open(TareaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarea = tarea;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
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

    this.loadFromBackendWithRouteInformationsConcepto().subscribe({
      next: (res: EntityArrayResponseType5) => {
        this.onResponseSuccessConcepto(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  Cancel(): void {
    window.history.back();
  }

  update(): void {
    this.tareaService.updateXLS(this.tareas!).subscribe((data: any) => {
      saveAs(data, 'Actualizar_Tareas.xls');
    });

    const modalRef = this.modalService.open(TareaSubmitDialogComponent, { size: 'lg', backdrop: 'static' });
    // unsubscribe not needed because closed completes on modal close

    modalRef.closed.subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
        this.load();
      },
    });
  }

  showFilters(): void {
    if (this.showFilter) {
      this.showFilter = false;
      this.filterId = 0;
      this.filterOId = 0;
      this.filterSId = 0;
      this.filterCId = 0;
      this.loadFromBackendWithRouteInformations().subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
    } else {
      this.showFilter = true;
    }
  }

  onChangeId(): void {
    if (this.Id !== 0) {
      this.filterId = this.Id;
    } else {
      if (this.findForm.get('id')!.value! == null) {
        this.filterId = 0;
      } else {
        this.filterId = this.Id;
      }
    }
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  onChangeObra(): void {
    if (this.oId !== 0) {
      this.filterOId = this.oId;
    } else {
      if (this.findForm.get('obra')!.value! == null) {
        this.filterOId = 0;
      } else {
        this.obra = this.findForm.get('obra')!.value!;
        this.filterOId = this.obra!.id;
      }
      this.loadFromBackendWithRouteInformations().subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
    }
  }

  onChangeSubcontratista(): void {
    if (this.findForm.get('subcontratista')!.value! == null) {
      this.filterSId = 0;
    } else {
      this.subco = this.findForm.get('subcontratista')!.value!;
      this.filterSId = this.subco!.id;
    }

    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  onChangeConcepto(): void {
    if (this.findForm.get('concepto')!.value! == null) {
      this.filterCId = 0;
    } else {
      this.concepto = this.findForm.get('concepto')!.value!;
      this.filterCId = this.concepto!.id;
    }
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
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

  protected loadFromBackendWithRouteInformationsConcepto(): Observable<EntityArrayResponseType5> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendConcepto(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.tareas = this.refineData(dataFromBody);
  }

  protected onResponseSuccessObra(response: EntityArrayResponseType3): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyObra(response.body);
    this.obras = this.refineDataObra(dataFromBody);
  }

  protected onResponseSuccessSubcontratista(response: EntityArrayResponseType4): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodySubcontratista(response.body);
    this.subcontratistas = this.refineDataSubcontratista(dataFromBody);
  }

  protected onResponseSuccessConcepto(response: EntityArrayResponseType5): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyConcepto(response.body);
    this.conceptos = this.refineDataConcepto(dataFromBody);
  }

  protected refineData(data: ITarea[]): ITarea[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataObra(data: IObra[]): IObra[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataSubcontratista(data: ISubcontratista[]): ISubcontratista[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataConcepto(data: IConcepto[]): IConcepto[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: ITarea[] | null): ITarea[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodyObra(data: IObra[] | null): IObra[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodySubcontratista(data: ISubcontratista[] | null): ISubcontratista[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodyConcepto(data: IConcepto[] | null): IConcepto[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    if (this.filterOId !== 0) {
      this.oId = this.filterOId;
    }
    if (this.filterSId !== 0) {
      this.sId = this.filterSId;
    } else {
      this.sId = 0;
    }
    if (this.filterId !== 0) {
      this.Id = this.filterId;
    }
    const queryObject = {
      eagerload: true,
      sort: this.getSortQueryParam(predicate, ascending),
      'id.equals': this.Id,
      'obraId.equals': this.oId,
      'subcontratistaId.equals': this.sId,
      'conceptoId.equals': this.filterCId,
    };
    return this.tareaService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected queryBackendObra(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType3> {
    this.isLoadingObra = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'id.equals': this.oId,
    };
    return this.obraService.query(queryObject).pipe(tap(() => (this.isLoadingObra = false)));
  }

  protected queryBackendSubcontratista(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType4> {
    this.isLoadingSubcontratista = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'obraId.equals': this.oId,
    };
    return this.subcontratistaService.query(queryObject).pipe(tap(() => (this.isLoadingSubcontratista = false)));
  }

  protected queryBackendConcepto(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType5> {
    this.isLoadingConcepto = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.conceptoService.query(queryObject).pipe(tap(() => (this.isLoadingConcepto = false)));
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
