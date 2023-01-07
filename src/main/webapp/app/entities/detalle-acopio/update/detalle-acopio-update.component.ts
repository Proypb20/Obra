import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DetalleAcopioFormService, DetalleAcopioFormGroup } from './detalle-acopio-form.service';
import { IDetalleAcopio } from '../detalle-acopio.model';
import { DetalleAcopioService } from '../service/detalle-acopio.service';
import { IAcopio } from 'app/entities/acopio/acopio.model';
import { AcopioService } from 'app/entities/acopio/service/acopio.service';
import { IDetalleListaPrecio } from 'app/entities/detalle-lista-precio/detalle-lista-precio.model';
import { DetalleListaPrecioService } from 'app/entities/detalle-lista-precio/service/detalle-lista-precio.service';
import { Estado } from 'app/entities/enumerations/estado.model';

@Component({
  selector: 'jhi-detalle-acopio-update',
  templateUrl: './detalle-acopio-update.component.html',
})
export class DetalleAcopioUpdateComponent implements OnInit {
  isSaving = false;
  detalleAcopio: IDetalleAcopio | null = null;
  estadoValues = Object.keys(Estado);

  acopiosSharedCollection: IAcopio[] = [];
  detalleListaPreciosSharedCollection: IDetalleListaPrecio[] = [];

  aId = 0;
  lpId = 0;

  editForm: DetalleAcopioFormGroup = this.detalleAcopioFormService.createDetalleAcopioFormGroup();

  constructor(
    protected detalleAcopioService: DetalleAcopioService,
    protected detalleAcopioFormService: DetalleAcopioFormService,
    protected acopioService: AcopioService,
    protected detalleListaPrecioService: DetalleListaPrecioService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAcopio = (o1: IAcopio | null, o2: IAcopio | null): boolean => this.acopioService.compareAcopio(o1, o2);

  compareDetalleListaPrecio = (o1: IDetalleListaPrecio | null, o2: IDetalleListaPrecio | null): boolean =>
    this.detalleListaPrecioService.compareDetalleListaPrecio(o1, o2);

  ngOnInit(): void {
    this.aId = history.state?.aId;
    this.lpId = history.state?.lpId;
    this.activatedRoute.data.subscribe(({ detalleAcopio }) => {
      this.detalleAcopio = detalleAcopio;
      if (detalleAcopio) {
        this.updateForm(detalleAcopio);
      }
      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detalleAcopio = this.detalleAcopioFormService.getDetalleAcopio(this.editForm);
    if (detalleAcopio.id !== null) {
      this.subscribeToSaveResponse(this.detalleAcopioService.update(detalleAcopio));
    } else {
      this.subscribeToSaveResponse(this.detalleAcopioService.create(detalleAcopio));
    }
  }

  onChangeProducto(): void {
    this.editForm.patchValue({ unitPrice: this.editForm.get('detalleListaPrecio')!.value!.amount });
    if (this.editForm.get('quantity')!.value !== null) {
      const amount1 = this.editForm.get('quantity')!.value! * this.editForm.get('detalleListaPrecio')!.value!.amount!;
      this.editForm.patchValue({ amount: amount1 });
    }
  }

  onChangeQuantity(): void {
    if (this.editForm.get('unitPrice')!.value !== null) {
      const amount1 = this.editForm.get('quantity')!.value! * this.editForm.get('unitPrice')!.value!;
      this.editForm.patchValue({ amount: amount1 });
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleAcopio>>): void {
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

  protected updateForm(detalleAcopio: IDetalleAcopio): void {
    this.detalleAcopio = detalleAcopio;
    this.detalleAcopioFormService.resetForm(this.editForm, detalleAcopio);

    this.acopiosSharedCollection = this.acopioService.addAcopioToCollectionIfMissing<IAcopio>(
      this.acopiosSharedCollection,
      detalleAcopio.acopio
    );
    this.detalleListaPreciosSharedCollection =
      this.detalleListaPrecioService.addDetalleListaPrecioToCollectionIfMissing<IDetalleListaPrecio>(
        this.detalleListaPreciosSharedCollection,
        detalleAcopio.detalleListaPrecio
      );
  }

  protected loadRelationshipsOptions(): void {
    this.acopioService
      .query({ 'id.equals': this.aId })
      .pipe(map((res: HttpResponse<IAcopio[]>) => res.body ?? []))
      .pipe(map((acopios: IAcopio[]) => this.acopioService.addAcopioToCollectionIfMissing<IAcopio>(acopios, this.detalleAcopio?.acopio)))
      .subscribe((acopios: IAcopio[]) => (this.acopiosSharedCollection = acopios));

    this.detalleListaPrecioService
      .query({ 'listaPrecioId.equals': this.lpId })
      .pipe(map((res: HttpResponse<IDetalleListaPrecio[]>) => res.body ?? []))
      .pipe(
        map((detalleListaPrecios: IDetalleListaPrecio[]) =>
          this.detalleListaPrecioService.addDetalleListaPrecioToCollectionIfMissing<IDetalleListaPrecio>(
            detalleListaPrecios,
            this.detalleAcopio?.detalleListaPrecio
          )
        )
      )
      .subscribe((detalleListaPrecios: IDetalleListaPrecio[]) => (this.detalleListaPreciosSharedCollection = detalleListaPrecios));
  }
}
