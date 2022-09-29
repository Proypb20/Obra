import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransaccion } from '../transaccion.model';

@Component({
  selector: 'jhi-transaccion-detail',
  templateUrl: './transaccion-detail.component.html',
})
export class TransaccionDetailComponent implements OnInit {
  transaccion: ITransaccion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaccion }) => {
      this.transaccion = transaccion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
