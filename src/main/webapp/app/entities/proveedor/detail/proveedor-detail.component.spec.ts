import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProveedorDetailComponent } from './proveedor-detail.component';

describe('Proveedor Management Detail Component', () => {
  let comp: ProveedorDetailComponent;
  let fixture: ComponentFixture<ProveedorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProveedorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ proveedor: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProveedorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProveedorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load proveedor on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.proveedor).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
