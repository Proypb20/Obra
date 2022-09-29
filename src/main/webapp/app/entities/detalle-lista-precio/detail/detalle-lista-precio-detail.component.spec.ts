import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DetalleListaPrecioDetailComponent } from './detalle-lista-precio-detail.component';

describe('DetalleListaPrecio Management Detail Component', () => {
  let comp: DetalleListaPrecioDetailComponent;
  let fixture: ComponentFixture<DetalleListaPrecioDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetalleListaPrecioDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ detalleListaPrecio: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DetalleListaPrecioDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DetalleListaPrecioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load detalleListaPrecio on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.detalleListaPrecio).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
