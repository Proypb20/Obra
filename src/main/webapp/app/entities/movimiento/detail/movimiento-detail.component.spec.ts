import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MovimientoDetailComponent } from './movimiento-detail.component';

describe('Movimiento Management Detail Component', () => {
  let comp: MovimientoDetailComponent;
  let fixture: ComponentFixture<MovimientoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MovimientoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ movimiento: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MovimientoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MovimientoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load movimiento on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.movimiento).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
