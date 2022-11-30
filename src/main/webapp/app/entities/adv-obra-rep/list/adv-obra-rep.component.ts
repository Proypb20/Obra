import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, Validators } from '@angular/forms';
import { IAdvObraRep } from '../adv-obra-rep.model';
import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, AdvObraRepService } from '../service/adv-obra-rep.service';
import { EntityArrayResponseType as EntityArrayResponseType2, ObraService } from 'app/entities/obra/service/obra.service';
import {
  EntityArrayResponseType as EntityArrayResponseType3,
  SubcontratistaService,
} from 'app/entities/subcontratista/service/subcontratista.service';
import { SortService } from 'app/shared/sort/sort.service';
import { IObra } from 'app/entities/obra/obra.model';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-adv-obra-rep',
  templateUrl: './adv-obra-rep.component.html',
})
export class AdvObraRepComponent implements OnInit {
  advObraReps?: IAdvObraRep[];
  obras?: IObra[];
  subcontratistas?: ISubcontratista[];
  isLoading = false;
  isLoadingObra = false;
  isLoadingSubcontratista = false;
  predicate = 'id';
  ascending = true;
  ob?: any;
  subco?: any;

  findForm = this.fb.group({
    obra: [null, Validators.required],
    subcontratista: [null],
  });

  constructor(
    protected advObraRepService: AdvObraRepService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected obraService: ObraService,
    protected subcontratistaService: SubcontratistaService,
    protected fb: FormBuilder
  ) {}
  trackId = (_index: number, item: IAdvObraRep): number => this.advObraRepService.getAdvObraRepIdentifier(item);

  trackIdObra = (_index: number, item: IObra): number => this.obraService.getObraIdentifier(item);

  trackIdSubcontratista = (_index: number, item: ISubcontratista): number => this.subcontratistaService.getSubcontratistaIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loadFromBackendWithRouteInformationsObra().subscribe({
      next: (res: EntityArrayResponseType2) => {
        this.onResponseSuccessObra(res);
      },
    });
  }

  onChangeObra(): void {
    this.ob = this.findForm.get('obra')!.value!;
    this.loadFromBackendWithRouteInformationsSubcontratista().subscribe({
      next: (res: EntityArrayResponseType3) => {
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

  find(): void {
    this.ob = this.findForm.get('obra')!.value!;
    this.subco = this.findForm.get('subcontratista')!.value!;
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  exportXLS(): void {
    this.advObraRepService.generateXLS(this.ob!.id!).subscribe((data: any) => {
      saveAs(data, 'Avance_Obra_' + this.ob!.name! + '.xls');
    });
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected loadFromBackendWithRouteInformationsObra(): Observable<EntityArrayResponseType2> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendObra(this.predicate, this.ascending))
    );
  }

  protected loadFromBackendWithRouteInformationsSubcontratista(): Observable<EntityArrayResponseType3> {
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
    this.advObraReps = this.refineData(dataFromBody);
  }

  protected onResponseSuccessObra(response: EntityArrayResponseType2): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyObra(response.body);
    this.obras = this.refineDataObra(dataFromBody);
  }

  protected onResponseSuccessSubcontratista(response: EntityArrayResponseType3): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodySubcontratista(response.body);
    this.subcontratistas = this.refineDataSubcontratista(dataFromBody);
  }

  protected refineData(data: IAdvObraRep[]): IAdvObraRep[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataObra(data: IObra[]): IObra[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataSubcontratista(data: ISubcontratista[]): ISubcontratista[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IAdvObraRep[] | null): IAdvObraRep[] {
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
    let subcoId = 0;
    if (this.subco! !== null) {
      subcoId = this.subco!.id!;
    }
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'obraId.equals': this.ob!.id!,
      'subcontratistaId.equals': subcoId,
    };
    return this.advObraRepService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected queryBackendObra(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType2> {
    this.isLoadingObra = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.obraService.query(queryObject).pipe(tap(() => (this.isLoadingObra = false)));
  }

  protected queryBackendSubcontratista(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType3> {
    this.isLoadingSubcontratista = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'obraId.equals': this.ob!.id!,
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
