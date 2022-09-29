import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UnidadMedidaDetailComponent } from './unidad-medida-detail.component';

describe('UnidadMedida Management Detail Component', () => {
  let comp: UnidadMedidaDetailComponent;
  let fixture: ComponentFixture<UnidadMedidaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UnidadMedidaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ unidadMedida: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UnidadMedidaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UnidadMedidaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load unidadMedida on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.unidadMedida).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
