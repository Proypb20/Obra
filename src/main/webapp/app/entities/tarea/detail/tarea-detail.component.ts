import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarea } from '../tarea.model';

@Component({
  selector: 'jhi-tarea-detail',
  templateUrl: './tarea-detail.component.html',
})
export class TareaDetailComponent implements OnInit {
  tarea: ITarea | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarea }) => {
      this.tarea = tarea;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
