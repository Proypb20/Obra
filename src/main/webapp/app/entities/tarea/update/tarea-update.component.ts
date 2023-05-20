import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TareaFormService, TareaFormGroup } from './tarea-form.service';
import { ITarea } from '../tarea.model';
import { TareaService } from '../service/tarea.service';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SubcontratistaService } from 'app/entities/subcontratista/service/subcontratista.service';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { ConceptoService } from 'app/entities/concepto/service/concepto.service';
import { SortService } from 'app/shared/sort/sort.service';

@Component({
  selector: 'jhi-tarea-update',
  templateUrl: './tarea-update.component.html',
})
export class TareaUpdateComponent implements OnInit {
  isSaving = false;
  tarea: ITarea | null = null;

  obrasSharedCollection: IObra[] = [];
  subcontratistasSharedCollection: ISubcontratista[] = [];
  conceptosSharedCollection: IConcepto[] = [];

  editForm: TareaFormGroup = this.tareaFormService.createTareaFormGroup();

  oId = 0;

  selectedObra: IObra | null = null;

  constructor(
    protected tareaService: TareaService,
    protected tareaFormService: TareaFormService,
    protected obraService: ObraService,
    protected subcontratistaService: SubcontratistaService,
    protected conceptoService: ConceptoService,
    protected activatedRoute: ActivatedRoute,
    protected sortService: SortService
  ) {}

  compareObra = (o1: IObra | null, o2: IObra | null): boolean => this.obraService.compareObra(o1, o2);

  compareSubcontratista = (o1: ISubcontratista | null, o2: ISubcontratista | null): boolean =>
    this.subcontratistaService.compareSubcontratista(o1, o2);

  compareConcepto = (o1: IConcepto | null, o2: IConcepto | null): boolean => this.conceptoService.compareConcepto(o1, o2);

  ngOnInit(): void {
    this.oId = history.state?.oId;

    this.activatedRoute.data.subscribe(({ tarea }) => {
      this.tarea = tarea;
      if (tarea) {
        this.updateForm(tarea);
      }
      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarea = this.tareaFormService.getTarea(this.editForm);
    if (tarea.id !== null) {
      this.subscribeToSaveResponse(this.tareaService.update(tarea));
    } else {
      this.subscribeToSaveResponse(this.tareaService.create(tarea));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarea>>): void {
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

  protected updateForm(tarea: ITarea): void {
    this.tarea = tarea;
    this.tareaFormService.resetForm(this.editForm, tarea);

    this.obrasSharedCollection = this.obraService.addObraToCollectionIfMissing<IObra>(this.obrasSharedCollection, tarea.obra);
    this.subcontratistasSharedCollection = this.subcontratistaService.addSubcontratistaToCollectionIfMissing<ISubcontratista>(
      this.subcontratistasSharedCollection,
      tarea.subcontratista
    );
    this.conceptosSharedCollection = this.conceptoService.addConceptoToCollectionIfMissing<IConcepto>(
      this.conceptosSharedCollection,
      tarea.concepto
    );
  }

  protected refineDataObras(data: IObra[]): IObra[] {
    return data.sort(this.sortService.startSort('name', 1));
  }

  protected refineDataSubco(data: ISubcontratista[]): ISubcontratista[] {
    return data.sort(this.sortService.startSort('lastName', 1));
  }

  protected refineDataConcepto(data: IConcepto[]): IConcepto[] {
    return data.sort(this.sortService.startSort('name', 1));
  }

  protected loadRelationshipsOptions(): void {
    this.obraService
      .query({ 'id.equals': this.oId })
      .pipe(map((res: HttpResponse<IObra[]>) => res.body ?? []))
      .pipe(map((obras: IObra[]) => this.refineDataObras(this.obraService.addObraToCollectionIfMissing<IObra>(obras, this.tarea?.obra))))
      .subscribe((obras: IObra[]) => ((this.obrasSharedCollection = obras), (this.selectedObra = obras[0])));

    this.subcontratistaService
      .query({ 'obraId.equals': this.oId })
      .pipe(map((res: HttpResponse<ISubcontratista[]>) => res.body ?? []))
      .pipe(
        map((subcontratistas: ISubcontratista[]) =>
          this.refineDataSubco(
            this.subcontratistaService.addSubcontratistaToCollectionIfMissing<ISubcontratista>(subcontratistas, this.tarea?.subcontratista)
          )
        )
      )
      .subscribe((subcontratistas: ISubcontratista[]) => (this.subcontratistasSharedCollection = this.refineDataSubco(subcontratistas)));

    this.conceptoService
      .query()
      .pipe(map((res: HttpResponse<IConcepto[]>) => res.body ?? []))
      .pipe(
        map((conceptos: IConcepto[]) =>
          this.refineDataConcepto(this.conceptoService.addConceptoToCollectionIfMissing<IConcepto>(conceptos, this.tarea?.concepto))
        )
      )
      .subscribe((conceptos: IConcepto[]) => (this.conceptosSharedCollection = this.refineDataConcepto(conceptos)));
  }
}
