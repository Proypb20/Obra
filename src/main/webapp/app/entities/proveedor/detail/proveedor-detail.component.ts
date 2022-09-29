import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProveedor } from '../proveedor.model';

@Component({
  selector: 'jhi-proveedor-detail',
  templateUrl: './proveedor-detail.component.html',
})
export class ProveedorDetailComponent implements OnInit {
  proveedor: IProveedor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
