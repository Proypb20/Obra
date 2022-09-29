import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProvincia } from '../provincia.model';

@Component({
  selector: 'jhi-provincia-detail',
  templateUrl: './provincia-detail.component.html',
})
export class ProvinciaDetailComponent implements OnInit {
  provincia: IProvincia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ provincia }) => {
      this.provincia = provincia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
