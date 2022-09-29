import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnidadMedida } from '../unidad-medida.model';

@Component({
  selector: 'jhi-unidad-medida-detail',
  templateUrl: './unidad-medida-detail.component.html',
})
export class UnidadMedidaDetailComponent implements OnInit {
  unidadMedida: IUnidadMedida | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unidadMedida }) => {
      this.unidadMedida = unidadMedida;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
