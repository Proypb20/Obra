import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { UnidadMedidaFormService, UnidadMedidaFormGroup } from './unidad-medida-form.service';
import { IUnidadMedida } from '../unidad-medida.model';
import { UnidadMedidaService } from '../service/unidad-medida.service';

@Component({
  selector: 'jhi-unidad-medida-update',
  templateUrl: './unidad-medida-update.component.html',
})
export class UnidadMedidaUpdateComponent implements OnInit {
  isSaving = false;
  unidadMedida: IUnidadMedida | null = null;

  editForm: UnidadMedidaFormGroup = this.unidadMedidaFormService.createUnidadMedidaFormGroup();

  constructor(
    protected unidadMedidaService: UnidadMedidaService,
    protected unidadMedidaFormService: UnidadMedidaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unidadMedida }) => {
      this.unidadMedida = unidadMedida;
      if (unidadMedida) {
        this.updateForm(unidadMedida);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unidadMedida = this.unidadMedidaFormService.getUnidadMedida(this.editForm);
    if (unidadMedida.id !== null) {
      this.subscribeToSaveResponse(this.unidadMedidaService.update(unidadMedida));
    } else {
      this.subscribeToSaveResponse(this.unidadMedidaService.create(unidadMedida));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnidadMedida>>): void {
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

  protected updateForm(unidadMedida: IUnidadMedida): void {
    this.unidadMedida = unidadMedida;
    this.unidadMedidaFormService.resetForm(this.editForm, unidadMedida);
  }
}
