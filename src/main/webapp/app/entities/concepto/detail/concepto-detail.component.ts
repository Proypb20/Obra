import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConcepto } from '../concepto.model';

@Component({
  selector: 'jhi-concepto-detail',
  templateUrl: './concepto-detail.component.html',
})
export class ConceptoDetailComponent implements OnInit {
  concepto: IConcepto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concepto }) => {
      this.concepto = concepto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
