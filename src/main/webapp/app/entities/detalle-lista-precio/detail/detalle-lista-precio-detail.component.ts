import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetalleListaPrecio } from '../detalle-lista-precio.model';

@Component({
  selector: 'jhi-detalle-lista-precio-detail',
  templateUrl: './detalle-lista-precio-detail.component.html',
})
export class DetalleListaPrecioDetailComponent implements OnInit {
  detalleListaPrecio: IDetalleListaPrecio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalleListaPrecio }) => {
      this.detalleListaPrecio = detalleListaPrecio;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
