import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MovimientoFormService, MovimientoFormGroup } from './movimiento-form.service';
import { IMovimiento } from '../movimiento.model';
import { MovimientoService } from '../service/movimiento.service';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SubcontratistaService } from 'app/entities/subcontratista/service/subcontratista.service';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { ConceptoService } from 'app/entities/concepto/service/concepto.service';
import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';

@Component({
  selector: 'jhi-movimiento-update',
  templateUrl: './movimiento-update.component.html',
})
export class MovimientoUpdateComponent implements OnInit {
  isSaving = false;
  movimiento: IMovimiento | null = null;
  metodoPagoValues = Object.keys(MetodoPago);

  obrasSharedCollection: IObra[] = [];
  subcontratistasSharedCollection: ISubcontratista[] = [];
  conceptosSharedCollection: IConcepto[] = [];

  editForm: MovimientoFormGroup = this.movimientoFormService.createMovimientoFormGroup();

  constructor(
    protected movimientoService: MovimientoService,
    protected movimientoFormService: MovimientoFormService,
    protected obraService: ObraService,
    protected subcontratistaService: SubcontratistaService,
    protected conceptoService: ConceptoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareObra = (o1: IObra | null, o2: IObra | null): boolean => this.obraService.compareObra(o1, o2);

  compareSubcontratista = (o1: ISubcontratista | null, o2: ISubcontratista | null): boolean =>
    this.subcontratistaService.compareSubcontratista(o1, o2);

  compareConcepto = (o1: IConcepto | null, o2: IConcepto | null): boolean => this.conceptoService.compareConcepto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ movimiento }) => {
      this.movimiento = movimiento;
      if (movimiento) {
        this.updateForm(movimiento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const movimiento = this.movimientoFormService.getMovimiento(this.editForm);
    if (movimiento.id !== null) {
      this.subscribeToSaveResponse(this.movimientoService.update(movimiento));
    } else {
      this.subscribeToSaveResponse(this.movimientoService.create(movimiento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimiento>>): void {
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

  protected updateForm(movimiento: IMovimiento): void {
    this.movimiento = movimiento;
    this.movimientoFormService.resetForm(this.editForm, movimiento);

    this.obrasSharedCollection = this.obraService.addObraToCollectionIfMissing<IObra>(this.obrasSharedCollection, movimiento.obra);
    this.subcontratistasSharedCollection = this.subcontratistaService.addSubcontratistaToCollectionIfMissing<ISubcontratista>(
      this.subcontratistasSharedCollection,
      movimiento.subcontratista
    );
    this.conceptosSharedCollection = this.conceptoService.addConceptoToCollectionIfMissing<IConcepto>(
      this.conceptosSharedCollection,
      movimiento.concepto
    );
  }

  protected loadRelationshipsOptions(): void {
    this.obraService
      .query()
      .pipe(map((res: HttpResponse<IObra[]>) => res.body ?? []))
      .pipe(map((obras: IObra[]) => this.obraService.addObraToCollectionIfMissing<IObra>(obras, this.movimiento?.obra)))
      .subscribe((obras: IObra[]) => (this.obrasSharedCollection = obras));

    this.subcontratistaService
      .query()
      .pipe(map((res: HttpResponse<ISubcontratista[]>) => res.body ?? []))
      .pipe(
        map((subcontratistas: ISubcontratista[]) =>
          this.subcontratistaService.addSubcontratistaToCollectionIfMissing<ISubcontratista>(
            subcontratistas,
            this.movimiento?.subcontratista
          )
        )
      )
      .subscribe((subcontratistas: ISubcontratista[]) => (this.subcontratistasSharedCollection = subcontratistas));

    this.conceptoService
      .query()
      .pipe(map((res: HttpResponse<IConcepto[]>) => res.body ?? []))
      .pipe(
        map((conceptos: IConcepto[]) =>
          this.conceptoService.addConceptoToCollectionIfMissing<IConcepto>(conceptos, this.movimiento?.concepto)
        )
      )
      .subscribe((conceptos: IConcepto[]) => (this.conceptosSharedCollection = conceptos));
  }
}
