import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, Validators } from '@angular/forms';
import { ISumTrxRep } from '../sum-trx-rep.model';
import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, SumTrxRepService } from '../service/sum-trx-rep.service';
import { EntityArrayResponseType as EntityArrayResponseType2, ObraService } from 'app/entities/obra/service/obra.service';
import { SortService } from 'app/shared/sort/sort.service';
import { IObra } from 'app/entities/obra/obra.model';

@Component({
  selector: 'jhi-sum-trx-rep',
  templateUrl: './sum-trx-rep.component.html',
})
export class SumTrxRepComponent implements OnInit {
  sumTrxReps?: ISumTrxRep[];
  obras?: IObra[];
  isLoading = false;
  isLoadingObra = false;
  showDet = false;
  predicate = 'id';
  ascending = true;
  ChildOId = 0;

  findForm = this.fb.group({
    obra: [null, Validators.required],
    dateFrom: [null, Validators.required],
    dateTo: [null, Validators.required],
  });

  constructor(
    protected sumTrxRepService: SumTrxRepService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected obraService: ObraService,
    protected fb: FormBuilder
  ) {}
  trackId = (_index: number, item: ISumTrxRep): number => this.sumTrxRepService.getSumTrxRepIdentifier(item);

  trackIdObra = (_index: number, item: IObra): number => this.obraService.getObraIdentifier(item);

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

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  Cancel(): void {
    window.history.back();
  }

  find(): void {
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
    this.sumTrxReps = this.refineData(dataFromBody);
  }

  protected onResponseSuccessObra(response: EntityArrayResponseType2): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyObra(response.body);
    this.obras = this.refineDataObra(dataFromBody);
  }

  protected refineData(data: ISumTrxRep[]): ISumTrxRep[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected refineDataObra(data: IObra[]): IObra[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: ISumTrxRep[] | null): ISumTrxRep[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseBodyObra(data: IObra[] | null): IObra[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;

    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
      'obraId.equals': this.findForm.get('obra')!.value!,
      'fecha.greaterThan': this.findForm.get('dateFrom')!.value!,
      'fecha.lessThan': this.findForm.get('dateTo')!.value!,
    };
    return this.sumTrxRepService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
