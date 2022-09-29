import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AcopioFormService, AcopioFormGroup } from './acopio-form.service';
import { IAcopio } from '../acopio.model';
import { AcopioService } from '../service/acopio.service';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

@Component({
  selector: 'jhi-acopio-update',
  templateUrl: './acopio-update.component.html',
})
export class AcopioUpdateComponent implements OnInit {
  isSaving = false;
  acopio: IAcopio | null = null;

  obrasSharedCollection: IObra[] = [];
  proveedorsSharedCollection: IProveedor[] = [];

  editForm: AcopioFormGroup = this.acopioFormService.createAcopioFormGroup();

  constructor(
    protected acopioService: AcopioService,
    protected acopioFormService: AcopioFormService,
    protected obraService: ObraService,
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareObra = (o1: IObra | null, o2: IObra | null): boolean => this.obraService.compareObra(o1, o2);

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acopio }) => {
      this.acopio = acopio;
      if (acopio) {
        this.updateForm(acopio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const acopio = this.acopioFormService.getAcopio(this.editForm);
    if (acopio.id !== null) {
      this.subscribeToSaveResponse(this.acopioService.update(acopio));
    } else {
      this.subscribeToSaveResponse(this.acopioService.create(acopio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcopio>>): void {
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

  protected updateForm(acopio: IAcopio): void {
    this.acopio = acopio;
    this.acopioFormService.resetForm(this.editForm, acopio);

    this.obrasSharedCollection = this.obraService.addObraToCollectionIfMissing<IObra>(this.obrasSharedCollection, acopio.obra);
    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      acopio.proveedor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.obraService
      .query()
      .pipe(map((res: HttpResponse<IObra[]>) => res.body ?? []))
      .pipe(map((obras: IObra[]) => this.obraService.addObraToCollectionIfMissing<IObra>(obras, this.acopio?.obra)))
      .subscribe((obras: IObra[]) => (this.obrasSharedCollection = obras));

    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(proveedors, this.acopio?.proveedor)
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));
  }
}
