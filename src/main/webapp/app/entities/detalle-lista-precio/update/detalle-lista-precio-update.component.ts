import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DetalleListaPrecioFormService, DetalleListaPrecioFormGroup } from './detalle-lista-precio-form.service';
import { IDetalleListaPrecio } from '../detalle-lista-precio.model';
import { DetalleListaPrecioService } from '../service/detalle-lista-precio.service';
import { IListaPrecio } from 'app/entities/lista-precio/lista-precio.model';
import { ListaPrecioService } from 'app/entities/lista-precio/service/lista-precio.service';

@Component({
  selector: 'jhi-detalle-lista-precio-update',
  templateUrl: './detalle-lista-precio-update.component.html',
})
export class DetalleListaPrecioUpdateComponent implements OnInit {
  isSaving = false;
  detalleListaPrecio: IDetalleListaPrecio | null = null;

  listaPreciosSharedCollection: IListaPrecio[] = [];
  lpId = 0;

  editForm: DetalleListaPrecioFormGroup = this.detalleListaPrecioFormService.createDetalleListaPrecioFormGroup();

  constructor(
    protected detalleListaPrecioService: DetalleListaPrecioService,
    protected detalleListaPrecioFormService: DetalleListaPrecioFormService,
    protected listaPrecioService: ListaPrecioService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareListaPrecio = (o1: IListaPrecio | null, o2: IListaPrecio | null): boolean => this.listaPrecioService.compareListaPrecio(o1, o2);

  ngOnInit(): void {
    this.lpId = history.state?.lpId;
    this.activatedRoute.data.subscribe(({ detalleListaPrecio }) => {
      this.detalleListaPrecio = detalleListaPrecio;
      if (detalleListaPrecio) {
        this.updateForm(detalleListaPrecio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detalleListaPrecio = this.detalleListaPrecioFormService.getDetalleListaPrecio(this.editForm);
    if (detalleListaPrecio.id !== null) {
      this.subscribeToSaveResponse(this.detalleListaPrecioService.update(detalleListaPrecio));
    } else {
      this.subscribeToSaveResponse(this.detalleListaPrecioService.create(detalleListaPrecio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleListaPrecio>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(detalleListaPrecio: IDetalleListaPrecio): void {
    this.detalleListaPrecio = detalleListaPrecio;
    this.detalleListaPrecioFormService.resetForm(this.editForm, detalleListaPrecio);

    this.listaPreciosSharedCollection = this.listaPrecioService.addListaPrecioToCollectionIfMissing<IListaPrecio>(
      this.listaPreciosSharedCollection,
      detalleListaPrecio.listaPrecio
    );
  }

  protected loadRelationshipsOptions(): void {
    this.listaPrecioService
      .query({ 'id.equals': this.lpId })
      .pipe(map((res: HttpResponse<IListaPrecio[]>) => res.body ?? []))
      .pipe(
        map((listaPrecios: IListaPrecio[]) =>
          this.listaPrecioService.addListaPrecioToCollectionIfMissing<IListaPrecio>(listaPrecios, this.detalleListaPrecio?.listaPrecio)
        )
      )
      .subscribe((listaPrecios: IListaPrecio[]) => (this.listaPreciosSharedCollection = listaPrecios));
  }
}
