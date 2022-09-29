import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TipoComprobanteFormService, TipoComprobanteFormGroup } from './tipo-comprobante-form.service';
import { ITipoComprobante } from '../tipo-comprobante.model';
import { TipoComprobanteService } from '../service/tipo-comprobante.service';

@Component({
  selector: 'jhi-tipo-comprobante-update',
  templateUrl: './tipo-comprobante-update.component.html',
})
export class TipoComprobanteUpdateComponent implements OnInit {
  isSaving = false;
  tipoComprobante: ITipoComprobante | null = null;

  editForm: TipoComprobanteFormGroup = this.tipoComprobanteFormService.createTipoComprobanteFormGroup();

  constructor(
    protected tipoComprobanteService: TipoComprobanteService,
    protected tipoComprobanteFormService: TipoComprobanteFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoComprobante }) => {
      this.tipoComprobante = tipoComprobante;
      if (tipoComprobante) {
        this.updateForm(tipoComprobante);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoComprobante = this.tipoComprobanteFormService.getTipoComprobante(this.editForm);
    if (tipoComprobante.id !== null) {
      this.subscribeToSaveResponse(this.tipoComprobanteService.update(tipoComprobante));
    } else {
      this.subscribeToSaveResponse(this.tipoComprobanteService.create(tipoComprobante));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoComprobante>>): void {
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

  protected updateForm(tipoComprobante: ITipoComprobante): void {
    this.tipoComprobante = tipoComprobante;
    this.tipoComprobanteFormService.resetForm(this.editForm, tipoComprobante);
  }
}
