import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SubcontratistaFormService, SubcontratistaFormGroup } from './subcontratista-form.service';
import { ISubcontratista } from '../subcontratista.model';
import { SubcontratistaService } from '../service/subcontratista.service';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';

@Component({
  selector: 'jhi-subcontratista-update',
  templateUrl: './subcontratista-update.component.html',
})
export class SubcontratistaUpdateComponent implements OnInit {
  isSaving = false;
  subcontratista: ISubcontratista | null = null;

  obrasSharedCollection: IObra[] = [];

  editForm: SubcontratistaFormGroup = this.subcontratistaFormService.createSubcontratistaFormGroup();

  constructor(
    protected subcontratistaService: SubcontratistaService,
    protected subcontratistaFormService: SubcontratistaFormService,
    protected obraService: ObraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareObra = (o1: IObra | null, o2: IObra | null): boolean => this.obraService.compareObra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subcontratista }) => {
      this.subcontratista = subcontratista;
      if (subcontratista) {
        this.updateForm(subcontratista);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subcontratista = this.subcontratistaFormService.getSubcontratista(this.editForm);
    if (subcontratista.id !== null) {
      this.subscribeToSaveResponse(this.subcontratistaService.update(subcontratista));
    } else {
      this.subscribeToSaveResponse(this.subcontratistaService.create(subcontratista));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubcontratista>>): void {
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

  protected updateForm(subcontratista: ISubcontratista): void {
    this.subcontratista = subcontratista;
    this.subcontratistaFormService.resetForm(this.editForm, subcontratista);

    this.obrasSharedCollection = this.obraService.addObraToCollectionIfMissing<IObra>(
      this.obrasSharedCollection,
      ...(subcontratista.obras ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.obraService
      .query()
      .pipe(map((res: HttpResponse<IObra[]>) => res.body ?? []))
      .pipe(map((obras: IObra[]) => this.obraService.addObraToCollectionIfMissing<IObra>(obras, ...(this.subcontratista?.obras ?? []))))
      .subscribe((obras: IObra[]) => (this.obrasSharedCollection = obras));
  }
}
