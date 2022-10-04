import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ObraFormService, ObraFormGroup } from './obra-form.service';
import { IObra } from '../obra.model';
import { ObraService } from '../service/obra.service';
import { IProvincia } from 'app/entities/provincia/provincia.model';
import { ProvinciaService } from 'app/entities/provincia/service/provincia.service';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SubcontratistaService } from 'app/entities/subcontratista/service/subcontratista.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

@Component({
  selector: 'jhi-obra-update',
  templateUrl: './obra-update.component.html',
})
export class ObraUpdateComponent implements OnInit {
  isSaving = false;
  obra: IObra | null = null;

  provinciasSharedCollection: IProvincia[] = [];
  subcontratistasSharedCollection: ISubcontratista[] = [];
  clientesSharedCollection: ICliente[] = [];

  editForm: ObraFormGroup = this.obraFormService.createObraFormGroup();

  constructor(
    protected obraService: ObraService,
    protected obraFormService: ObraFormService,
    protected provinciaService: ProvinciaService,
    protected subcontratistaService: SubcontratistaService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProvincia = (o1: IProvincia | null, o2: IProvincia | null): boolean => this.provinciaService.compareProvincia(o1, o2);

  compareSubcontratista = (o1: ISubcontratista | null, o2: ISubcontratista | null): boolean =>
    this.subcontratistaService.compareSubcontratista(o1, o2);

  compareCliente = (o1: ICliente | null, o2: ICliente | null): boolean => this.clienteService.compareCliente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ obra }) => {
      this.obra = obra;
      if (obra) {
        this.updateForm(obra);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const obra = this.obraFormService.getObra(this.editForm);
    if (obra.id !== null) {
      this.subscribeToSaveResponse(this.obraService.update(obra));
    } else {
      this.subscribeToSaveResponse(this.obraService.create(obra));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObra>>): void {
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

  protected updateForm(obra: IObra): void {
    this.obra = obra;
    this.obraFormService.resetForm(this.editForm, obra);

    this.provinciasSharedCollection = this.provinciaService.addProvinciaToCollectionIfMissing<IProvincia>(
      this.provinciasSharedCollection,
      obra.provincia
    );
    this.subcontratistasSharedCollection = this.subcontratistaService.addSubcontratistaToCollectionIfMissing<ISubcontratista>(
      this.subcontratistasSharedCollection,
      ...(obra.subcontratistas ?? [])
    );
    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing<ICliente>(
      this.clientesSharedCollection,
      ...(obra.clientes ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.provinciaService
      .query()
      .pipe(map((res: HttpResponse<IProvincia[]>) => res.body ?? []))
      .pipe(
        map((provincias: IProvincia[]) =>
          this.provinciaService.addProvinciaToCollectionIfMissing<IProvincia>(provincias, this.obra?.provincia)
        )
      )
      .subscribe((provincias: IProvincia[]) => (this.provinciasSharedCollection = provincias));

    this.subcontratistaService
      .query()
      .pipe(map((res: HttpResponse<ISubcontratista[]>) => res.body ?? []))
      .pipe(
        map((subcontratistas: ISubcontratista[]) =>
          this.subcontratistaService.addSubcontratistaToCollectionIfMissing<ISubcontratista>(
            subcontratistas,
            ...(this.obra?.subcontratistas ?? [])
          )
        )
      )
      .subscribe((subcontratistas: ISubcontratista[]) => (this.subcontratistasSharedCollection = subcontratistas));

    this.clienteService
      .query()
      .pipe(map((res: HttpResponse<ICliente[]>) => res.body ?? []))
      .pipe(
        map((clientes: ICliente[]) =>
          this.clienteService.addClienteToCollectionIfMissing<ICliente>(clientes, ...(this.obra?.clientes ?? []))
        )
      )
      .subscribe((clientes: ICliente[]) => (this.clientesSharedCollection = clientes));
  }
}
