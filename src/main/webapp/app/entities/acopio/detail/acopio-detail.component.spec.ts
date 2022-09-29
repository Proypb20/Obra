import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AcopioDetailComponent } from './acopio-detail.component';

describe('Acopio Management Detail Component', () => {
  let comp: AcopioDetailComponent;
  let fixture: ComponentFixture<AcopioDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AcopioDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ acopio: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AcopioDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AcopioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load acopio on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.acopio).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
