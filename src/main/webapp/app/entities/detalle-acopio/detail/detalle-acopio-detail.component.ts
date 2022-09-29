import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetalleAcopio } from '../detalle-acopio.model';

@Component({
  selector: 'jhi-detalle-acopio-detail',
  templateUrl: './detalle-acopio-detail.component.html',
})
export class DetalleAcopioDetailComponent implements OnInit {
  detalleAcopio: IDetalleAcopio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalleAcopio }) => {
      this.detalleAcopio = detalleAcopio;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
