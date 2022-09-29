import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConceptoDetailComponent } from './concepto-detail.component';

describe('Concepto Management Detail Component', () => {
  let comp: ConceptoDetailComponent;
  let fixture: ComponentFixture<ConceptoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConceptoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ concepto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ConceptoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConceptoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load concepto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.concepto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
