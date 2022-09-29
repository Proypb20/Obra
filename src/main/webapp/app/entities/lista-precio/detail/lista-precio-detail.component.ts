import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListaPrecio } from '../lista-precio.model';

@Component({
  selector: 'jhi-lista-precio-detail',
  templateUrl: './lista-precio-detail.component.html',
})
export class ListaPrecioDetailComponent implements OnInit {
  listaPrecio: IListaPrecio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listaPrecio }) => {
      this.listaPrecio = listaPrecio;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
