import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, Validators } from '@angular/forms';

import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { SubcoPayRepService } from '../service/subco-pay-rep.service';
import { EntityArrayResponseType as EntityArrayResponseType, ObraService } from 'app/entities/obra/service/obra.service';
import { SortService } from 'app/shared/sort/sort.service';
import { IObra } from 'app/entities/obra/obra.model';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-subco-pay-rep',
  templateUrl: './subco-pay-rep.component.html',
})
export class SubcoPayRepComponent implements OnInit {
  obras?: IObra[];
  isLoading = false;
  predicate = 'id';
  ascending = true;
  ob?: any;

  findForm = this.fb.group({
    obra: [null, Validators.required],
  });

  constructor(
    protected subcoPayRepService: SubcoPayRepService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected obraService: ObraService,
    protected fb: FormBuilder
  ) {}
  trackId = (_index: number, item: IObra): number => this.obraService.getObraIdentifier(item);

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

  exportXLS(): void {
    if (this.findForm.get('obra')?.value === null) {
      this.subcoPayRepService.generateXLS(0).subscribe((data: any) => {
        saveAs(data, 'Pagos_subcontratistas.xls');
      });
    } else {
      this.ob = this.findForm.get('obra')!.value!;
      this.subcoPayRepService.generateXLS(this.ob!.id!).subscribe((data: any) => {
        saveAs(data, 'Pagos_subcontratistas_' + this.ob!.name! + '.xls');
      });
    }
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.obras = this.refineData(dataFromBody);
  }

  protected refineData(data: IObra[]): IObra[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IObra[] | null): IObra[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.obraService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
