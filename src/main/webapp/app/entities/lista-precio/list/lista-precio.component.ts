import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IListaPrecio } from '../lista-precio.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, ListaPrecioService } from '../service/lista-precio.service';
import { ListaPrecioDeleteDialogComponent } from '../delete/lista-precio-delete-dialog.component';
import { ListaPrecioSubmitDialogComponent } from '../submit/lista-precio-submit-dialog.component';
import { SortService } from 'app/shared/sort/sort.service';

@Component({
  selector: 'jhi-lista-precio',
  templateUrl: './lista-precio.component.html',
})
export class ListaPrecioComponent implements OnInit {
  listaPrecios?: IListaPrecio[];
  isLoading = false;

  predicate = 'id';
  ascending = true;
  pId = 0;

  constructor(
    protected listaPrecioService: ListaPrecioService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IListaPrecio): number => this.listaPrecioService.getListaPrecioIdentifier(item);

  ngOnInit(): void {
    this.pId = history.state?.pId;
    this.load();
  }

  delete(listaPrecio: IListaPrecio): void {
    const modalRef = this.modalService.open(ListaPrecioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.listaPrecio = listaPrecio;
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
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  Cancel(): void {
    window.history.back();
  }

  submitFile(): void {
    const modalRef = this.modalService.open(ListaPrecioSubmitDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.idProveedor = history.state.pId;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
        this.load();
      },
    });
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
    this.listaPrecios = this.refineData(dataFromBody);
  }

  protected refineData(data: IListaPrecio[]): IListaPrecio[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IListaPrecio[] | null): IListaPrecio[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      eagerload: true,
      sort: this.getSortQueryParam(predicate, ascending),
      'proveedorId.equals': this.pId,
    };
    return this.listaPrecioService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
