import dayjs from 'dayjs/esm';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IMovimiento } from '../movimiento.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, MovimientoService } from '../service/movimiento.service';
import { MovimientoDeleteDialogComponent } from '../delete/movimiento-delete-dialog.component';
import { SortService } from 'app/shared/sort/sort.service';

import { EntityArrayResponseType as EntityArrayResponseType3, ObraService } from 'app/entities/obra/service/obra.service';
import {
  EntityArrayResponseType as EntityArrayResponseType4,
  SubcontratistaService,
} from 'app/entities/subcontratista/service/subcontratista.service';
import { EntityArrayResponseType as EntityArrayResponseType5, ConceptoService } from 'app/entities/concepto/service/concepto.service';
import {
  EntityArrayResponseType as EntityArrayResponseType6,
  TipoComprobanteService,
} from 'app/entities/tipo-comprobante/service/tipo-comprobante.service';
import { IObra } from 'app/entities/obra/obra.model';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';
import { ITipoComprobante } from 'app/entities/tipo-comprobante/tipo-comprobante.model';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-movimiento',
  templateUrl: './movimiento.component.html',
})
export class MovimientoComponent implements OnInit {
  movimientos?: IMovimiento[];
  isLoading = false;

  predicate = 'date';
  ascending = true;

  showFilter = false;
  filterOId = 0;
  filterSId = 0;
  filterDescripcion = null;
  filterCId = 0;
  filterTCId = 0;
  filterAmount = 0;
  filterMetodoPago = null;
  filterDate = null;
  obra: any;
  subco: any;
  concepto: any;
  tipoComprobante: any;
  amount: any;
  descripcion: any;
  metodoPago: any;
  // date: any;
  date?: dayjs.Dayjs | null;
  isLoadingMetodoPago = false;
  isLoadingObra = false;
  isLoadingSubcontratista = false;
  isLoadingConcepto = false;
  isLoadingTipoComprobante = false;
  obras?: IObra[];
  subcontratistas?: ISubcontratista[];
  conceptos?: IConcepto[];
  tipoComprobantes?: ITipoComprobante[];
  metodoPagoValues = Object.keys(MetodoPago);

  findForm = this.fb.group({
    date: [null],
    description: [null],
    metodoPago: [null],
    tipoComprobante: [null],
    amount: [null],
    obra: [null, Validators.required],
    subcontratista: [null, Validators.required],
    concepto: [null],
  });

  constructor(
    protected movimientoService: MovimientoService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected obraService: ObraService,
    protected subcontratistaService: SubcontratistaService,
    protected conceptoService: ConceptoService,
    protected tipoComprobanteService: TipoComprobanteService,
    protected fb: FormBuilder
  ) {}

  trackId = (_index: number, item: IMovimiento): number => this.movimientoService.getMovimientoIdentifier(item);

  ngOnInit(): void {
    this.filterMetodoPago = history.state?.mM;
    this.load();
  }

  Cancel(): void {
    window.history.back();
  }

  delete(movimiento: IMovimiento): void {
    const modalRef = this.modalService.open(MovimientoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.movimiento = movimiento;
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
    this.loadFromBackendWithRouteInformationsTipoComprobante().subscribe({
      next: (res: EntityArrayResponseType6) => {
        this.onResponseSuccessTipoComprobante(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  onChangeObra(): void {
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

  onChangeTipoComprobante(): void {
    if (this.findForm.get('tipoComprobante')!.value! == null) {
      this.filterTCId = 0;
    } else {
      this.tipoComprobante = this.findForm.get('tipoComprobante')!.value!;
      this.filterTCId = this.tipoComprobante!.id;
    }
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  onChangeAmount(): void {
    if (this.findForm.get('amount')!.value! == null) {
      this.filterAmount = 0;
    } else {
      this.amount = this.findForm.get('amount')!.value!;
      this.filterAmount = this.amount;
    }
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  onChangeDescripcion(): void {
    if (this.findForm.get('description')!.value! == null) {
      this.filterDescripcion = null;
    } else {
      this.descripcion = this.findForm.get('description')!.value!;
      this.filterDescripcion = this.descripcion;
    }
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  onChangeMetodoPago(): void {
    if (this.findForm.get('metodoPago')!.value! == null) {
      this.filterMetodoPago = null;
    } else {
      this.metodoPago = this.findForm.get('metodoPago')!.value!;
      this.filterMetodoPago = this.metodoPago;
    }
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  onChangeDate(): void {
    if (this.findForm.get('date')!.value! == null) {
      this.filterDate = null;
    } else {
      this.date = this.findForm.get('date')!.value!;
      this.filterDate = this.date;
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
      this.filterOId = 0;
      this.filterSId = 0;
      this.filterDescripcion = null;
      this.filterCId = 0;
      this.filterTCId = 0;
      this.filterAmount = 0;
      this.filterMetodoPago = null;
      this.filterDate = null;
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

  protected loadFromBackendWithRouteInformationsTipoComprobante(): Observable<EntityArrayResponseType6> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendTipoComprobante(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = 'date';
    this.ascending = sort[1] === DESC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.movimientos = this.refineData(dataFromBody);
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

  protected onResponseSuccessTipoComprobante(response: EntityArrayResponseType6): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyTipoComprobante(response.body);
    this.tipoComprobantes = this.refineDataTipoComprobante(dataFromBody);
  }

  protected refineData(data: IMovimiento[]): IMovimiento[] {
    return data.sort(this.sortService.startSortDate(this.predicate, this.ascending ? 1 : -1));
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

  protected refineDataTipoComprobante(data: ITipoComprobante[]): ITipoComprobante[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IMovimiento[] | null): IMovimiento[] {
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

  protected fillComponentAttributesFromResponseBodyTipoComprobante(data: ITipoComprobante[] | null): ITipoComprobante[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      eagerload: true,
      sort: this.getSortQueryParam(predicate, ascending),
      'obraId.equals': this.filterOId,
      'subcontratistaId.equals': this.filterSId,
      'conceptoId.equals': this.filterCId,
      'tipoComprobanteId.equals': this.filterTCId,
      'amount.equals': this.filterAmount,
      'description.contains': this.filterDescripcion,
      'metodoPago.equals': this.filterMetodoPago,
      'date.equals': this.filterDate,
    };
    return this.movimientoService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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

  protected queryBackendConcepto(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType5> {
    this.isLoadingConcepto = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.conceptoService.query(queryObject).pipe(tap(() => (this.isLoadingConcepto = false)));
  }

  protected queryBackendTipoComprobante(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType6> {
    this.isLoadingTipoComprobante = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.tipoComprobanteService.query(queryObject).pipe(tap(() => (this.isLoadingTipoComprobante = false)));
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
