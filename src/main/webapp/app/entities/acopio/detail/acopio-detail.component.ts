import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcopio } from '../acopio.model';

@Component({
  selector: 'jhi-acopio-detail',
  templateUrl: './acopio-detail.component.html',
})
export class AcopioDetailComponent implements OnInit {
  acopio: IAcopio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acopio }) => {
      this.acopio = acopio;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
