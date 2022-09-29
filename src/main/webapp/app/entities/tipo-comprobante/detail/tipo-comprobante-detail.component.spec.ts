import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TipoComprobanteDetailComponent } from './tipo-comprobante-detail.component';

describe('TipoComprobante Management Detail Component', () => {
  let comp: TipoComprobanteDetailComponent;
  let fixture: ComponentFixture<TipoComprobanteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipoComprobanteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tipoComprobante: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TipoComprobanteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TipoComprobanteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tipoComprobante on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tipoComprobante).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
