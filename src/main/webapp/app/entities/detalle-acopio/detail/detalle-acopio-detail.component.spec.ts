import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DetalleAcopioDetailComponent } from './detalle-acopio-detail.component';

describe('DetalleAcopio Management Detail Component', () => {
  let comp: DetalleAcopioDetailComponent;
  let fixture: ComponentFixture<DetalleAcopioDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetalleAcopioDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ detalleAcopio: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DetalleAcopioDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DetalleAcopioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load detalleAcopio on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.detalleAcopio).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
