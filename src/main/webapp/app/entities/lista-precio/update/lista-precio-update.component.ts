import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ListaPrecioFormService, ListaPrecioFormGroup } from './lista-precio-form.service';
import { IListaPrecio } from '../lista-precio.model';
import { ListaPrecioService } from '../service/lista-precio.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

@Component({
  selector: 'jhi-lista-precio-update',
  templateUrl: './lista-precio-update.component.html',
})
export class ListaPrecioUpdateComponent implements OnInit {
  isSaving = false;
  listaPrecio: IListaPrecio | null = null;

  proveedorsSharedCollection: IProveedor[] = [];

  editForm: ListaPrecioFormGroup = this.listaPrecioFormService.createListaPrecioFormGroup();

  constructor(
    protected listaPrecioService: ListaPrecioService,
    protected listaPrecioFormService: ListaPrecioFormService,
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listaPrecio }) => {
      this.listaPrecio = listaPrecio;
      if (listaPrecio) {
        this.updateForm(listaPrecio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const listaPrecio = this.listaPrecioFormService.getListaPrecio(this.editForm);
    if (listaPrecio.id !== null) {
      this.subscribeToSaveResponse(this.listaPrecioService.update(listaPrecio));
    } else {
      this.subscribeToSaveResponse(this.listaPrecioService.create(listaPrecio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IListaPrecio>>): void {
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

  protected updateForm(listaPrecio: IListaPrecio): void {
    this.listaPrecio = listaPrecio;
    this.listaPrecioFormService.resetForm(this.editForm, listaPrecio);

    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      listaPrecio.proveedor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(proveedors, this.listaPrecio?.proveedor)
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));
  }
}
