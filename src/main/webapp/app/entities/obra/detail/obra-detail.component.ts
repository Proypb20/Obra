import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObra } from '../obra.model';

@Component({
  selector: 'jhi-obra-detail',
  templateUrl: './obra-detail.component.html',
})
export class ObraDetailComponent implements OnInit {
  obra: IObra | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ obra }) => {
      this.obra = obra;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
