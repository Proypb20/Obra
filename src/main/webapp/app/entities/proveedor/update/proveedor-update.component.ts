import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProveedorFormService, ProveedorFormGroup } from './proveedor-form.service';
import { IProveedor } from '../proveedor.model';
import { ProveedorService } from '../service/proveedor.service';
import { IProvincia } from 'app/entities/provincia/provincia.model';
import { ProvinciaService } from 'app/entities/provincia/service/provincia.service';

@Component({
  selector: 'jhi-proveedor-update',
  templateUrl: './proveedor-update.component.html',
})
export class ProveedorUpdateComponent implements OnInit {
  isSaving = false;
  proveedor: IProveedor | null = null;

  provinciasSharedCollection: IProvincia[] = [];

  editForm: ProveedorFormGroup = this.proveedorFormService.createProveedorFormGroup();

  constructor(
    protected proveedorService: ProveedorService,
    protected proveedorFormService: ProveedorFormService,
    protected provinciaService: ProvinciaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProvincia = (o1: IProvincia | null, o2: IProvincia | null): boolean => this.provinciaService.compareProvincia(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
      if (proveedor) {
        this.updateForm(proveedor);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proveedor = this.proveedorFormService.getProveedor(this.editForm);
    if (proveedor.id !== null) {
      this.subscribeToSaveResponse(this.proveedorService.update(proveedor));
    } else {
      this.subscribeToSaveResponse(this.proveedorService.create(proveedor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>): void {
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

  protected updateForm(proveedor: IProveedor): void {
    this.proveedor = proveedor;
    this.proveedorFormService.resetForm(this.editForm, proveedor);

    this.provinciasSharedCollection = this.provinciaService.addProvinciaToCollectionIfMissing<IProvincia>(
      this.provinciasSharedCollection,
      proveedor.provincia
    );
  }

  protected loadRelationshipsOptions(): void {
    this.provinciaService
      .query()
      .pipe(map((res: HttpResponse<IProvincia[]>) => res.body ?? []))
      .pipe(
        map((provincias: IProvincia[]) =>
          this.provinciaService.addProvinciaToCollectionIfMissing<IProvincia>(provincias, this.proveedor?.provincia)
        )
      )
      .subscribe((provincias: IProvincia[]) => (this.provinciasSharedCollection = provincias));
  }
}
