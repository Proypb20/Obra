import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMovimiento } from '../movimiento.model';

@Component({
  selector: 'jhi-movimiento-detail',
  templateUrl: './movimiento-detail.component.html',
})
export class MovimientoDetailComponent implements OnInit {
  movimiento: IMovimiento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ movimiento }) => {
      this.movimiento = movimiento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
