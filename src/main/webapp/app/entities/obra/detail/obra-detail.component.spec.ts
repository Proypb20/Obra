import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObraDetailComponent } from './obra-detail.component';

describe('Obra Management Detail Component', () => {
  let comp: ObraDetailComponent;
  let fixture: ComponentFixture<ObraDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ObraDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ obra: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ObraDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ObraDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load obra on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.obra).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
