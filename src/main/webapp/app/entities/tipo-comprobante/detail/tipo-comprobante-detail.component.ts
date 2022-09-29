import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoComprobante } from '../tipo-comprobante.model';

@Component({
  selector: 'jhi-tipo-comprobante-detail',
  templateUrl: './tipo-comprobante-detail.component.html',
})
export class TipoComprobanteDetailComponent implements OnInit {
  tipoComprobante: ITipoComprobante | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoComprobante }) => {
      this.tipoComprobante = tipoComprobante;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
