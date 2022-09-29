import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubcontratista } from '../subcontratista.model';

@Component({
  selector: 'jhi-subcontratista-detail',
  templateUrl: './subcontratista-detail.component.html',
})
export class SubcontratistaDetailComponent implements OnInit {
  subcontratista: ISubcontratista | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subcontratista }) => {
      this.subcontratista = subcontratista;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
