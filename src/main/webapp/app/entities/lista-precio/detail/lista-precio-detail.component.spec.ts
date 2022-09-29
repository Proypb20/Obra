import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ListaPrecioDetailComponent } from './lista-precio-detail.component';

describe('ListaPrecio Management Detail Component', () => {
  let comp: ListaPrecioDetailComponent;
  let fixture: ComponentFixture<ListaPrecioDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaPrecioDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ listaPrecio: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ListaPrecioDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ListaPrecioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load listaPrecio on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.listaPrecio).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
