import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TransaccionFormService, TransaccionFormGroup } from './transaccion-form.service';
import { ITransaccion } from '../transaccion.model';
import { TransaccionService } from '../service/transaccion.service';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SubcontratistaService } from 'app/entities/subcontratista/service/subcontratista.service';
import { ITipoComprobante } from 'app/entities/tipo-comprobante/tipo-comprobante.model';
import { TipoComprobanteService } from 'app/entities/tipo-comprobante/service/tipo-comprobante.service';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { ConceptoService } from 'app/entities/concepto/service/concepto.service';
import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';

@Component({
  selector: 'jhi-transaccion-update',
  templateUrl: './transaccion-update.component.html',
})
export class TransaccionUpdateComponent implements OnInit {
  isSaving = false;
  transaccion: ITransaccion | null = null;
  metodoPagoValues = Object.keys(MetodoPago);

  obrasSharedCollection: IObra[] = [];
  subcontratistasSharedCollection: ISubcontratista[] = [];
  tipoComprobantesSharedCollection: ITipoComprobante[] = [];
  conceptosSharedCollection: IConcepto[] = [];

  oId = 0;

  editForm: TransaccionFormGroup = this.transaccionFormService.createTransaccionFormGroup();

  constructor(
    protected transaccionService: TransaccionService,
    protected transaccionFormService: TransaccionFormService,
    protected obraService: ObraService,
    protected subcontratistaService: SubcontratistaService,
    protected tipoComprobanteService: TipoComprobanteService,
    protected conceptoService: ConceptoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareObra = (o1: IObra | null, o2: IObra | null): boolean => this.obraService.compareObra(o1, o2);

  compareSubcontratista = (o1: ISubcontratista | null, o2: ISubcontratista | null): boolean =>
    this.subcontratistaService.compareSubcontratista(o1, o2);

  compareTipoComprobante = (o1: ITipoComprobante | null, o2: ITipoComprobante | null): boolean =>
    this.tipoComprobanteService.compareTipoComprobante(o1, o2);

  compareConcepto = (o1: IConcepto | null, o2: IConcepto | null): boolean => this.conceptoService.compareConcepto(o1, o2);

  ngOnInit(): void {
    this.oId = history.state.oId ?? 0;
    this.activatedRoute.data.subscribe(({ transaccion }) => {
      this.transaccion = transaccion;
      if (transaccion) {
        this.updateForm(transaccion);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaccion = this.transaccionFormService.getTransaccion(this.editForm);
    if (transaccion.id !== null) {
      this.subscribeToSaveResponse(this.transaccionService.update(transaccion));
    } else {
      this.subscribeToSaveResponse(this.transaccionService.create(transaccion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaccion>>): void {
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

  protected updateForm(transaccion: ITransaccion): void {
    this.transaccion = transaccion;
    this.transaccionFormService.resetForm(this.editForm, transaccion);

    this.obrasSharedCollection = this.obraService.addObraToCollectionIfMissing<IObra>(this.obrasSharedCollection, transaccion.obra);
    this.subcontratistasSharedCollection = this.subcontratistaService.addSubcontratistaToCollectionIfMissing<ISubcontratista>(
      this.subcontratistasSharedCollection,
      transaccion.subcontratista
    );
    this.tipoComprobantesSharedCollection = this.tipoComprobanteService.addTipoComprobanteToCollectionIfMissing<ITipoComprobante>(
      this.tipoComprobantesSharedCollection,
      transaccion.tipoComprobante
    );
    this.conceptosSharedCollection = this.conceptoService.addConceptoToCollectionIfMissing<IConcepto>(
      this.conceptosSharedCollection,
      transaccion.concepto
    );
  }

  protected loadRelationshipsOptions(): void {
    this.obraService
      .query({ 'id.equals': this.oId })
      .pipe(map((res: HttpResponse<IObra[]>) => res.body ?? []))
      .pipe(map((obras: IObra[]) => this.obraService.addObraToCollectionIfMissing<IObra>(obras, this.transaccion?.obra)))
      .subscribe((obras: IObra[]) => (this.obrasSharedCollection = obras));

    this.subcontratistaService
      .query({ 'obraId.equals': this.oId })
      .pipe(map((res: HttpResponse<ISubcontratista[]>) => res.body ?? []))
      .pipe(
        map((subcontratistas: ISubcontratista[]) =>
          this.subcontratistaService.addSubcontratistaToCollectionIfMissing<ISubcontratista>(
            subcontratistas,
            this.transaccion?.subcontratista
          )
        )
      )
      .subscribe((subcontratistas: ISubcontratista[]) => (this.subcontratistasSharedCollection = subcontratistas));

    this.tipoComprobanteService
      .query()
      .pipe(map((res: HttpResponse<ITipoComprobante[]>) => res.body ?? []))
      .pipe(
        map((tipoComprobantes: ITipoComprobante[]) =>
          this.tipoComprobanteService.addTipoComprobanteToCollectionIfMissing<ITipoComprobante>(
            tipoComprobantes,
            this.transaccion?.tipoComprobante
          )
        )
      )
      .subscribe((tipoComprobantes: ITipoComprobante[]) => (this.tipoComprobantesSharedCollection = tipoComprobantes));

    this.conceptoService
      .query()
      .pipe(map((res: HttpResponse<IConcepto[]>) => res.body ?? []))
      .pipe(
        map((conceptos: IConcepto[]) =>
          this.conceptoService.addConceptoToCollectionIfMissing<IConcepto>(conceptos, this.transaccion?.concepto)
        )
      )
      .subscribe((conceptos: IConcepto[]) => (this.conceptosSharedCollection = conceptos));
  }
}
