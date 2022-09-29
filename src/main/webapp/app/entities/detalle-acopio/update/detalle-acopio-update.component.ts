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

  editForm: DetalleAcopioFormGroup = this.detalleAcopioFormService.createDetalleAcopioFormGroup();

  constructor(
    protected detalleAcopioService: DetalleAcopioService,
    protected detalleAcopioFormService: DetalleAcopioFormService,
    protected acopioService: AcopioService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAcopio = (o1: IAcopio | null, o2: IAcopio | null): boolean => this.acopioService.compareAcopio(o1, o2);

  ngOnInit(): void {
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
  }

  protected loadRelationshipsOptions(): void {
    this.acopioService
      .query()
      .pipe(map((res: HttpResponse<IAcopio[]>) => res.body ?? []))
      .pipe(map((acopios: IAcopio[]) => this.acopioService.addAcopioToCollectionIfMissing<IAcopio>(acopios, this.detalleAcopio?.acopio)))
      .subscribe((acopios: IAcopio[]) => (this.acopiosSharedCollection = acopios));
  }
}
