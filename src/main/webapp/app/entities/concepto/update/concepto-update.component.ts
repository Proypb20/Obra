import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ConceptoFormService, ConceptoFormGroup } from './concepto-form.service';
import { IConcepto } from '../concepto.model';
import { ConceptoService } from '../service/concepto.service';

@Component({
  selector: 'jhi-concepto-update',
  templateUrl: './concepto-update.component.html',
})
export class ConceptoUpdateComponent implements OnInit {
  isSaving = false;
  concepto: IConcepto | null = null;

  editForm: ConceptoFormGroup = this.conceptoFormService.createConceptoFormGroup();

  constructor(
    protected conceptoService: ConceptoService,
    protected conceptoFormService: ConceptoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concepto }) => {
      this.concepto = concepto;
      if (concepto) {
        this.updateForm(concepto);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concepto = this.conceptoFormService.getConcepto(this.editForm);
    if (concepto.id !== null) {
      this.subscribeToSaveResponse(this.conceptoService.update(concepto));
    } else {
      this.subscribeToSaveResponse(this.conceptoService.create(concepto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcepto>>): void {
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

  protected updateForm(concepto: IConcepto): void {
    this.concepto = concepto;
    this.conceptoFormService.resetForm(this.editForm, concepto);
  }
}
