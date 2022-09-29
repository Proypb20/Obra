import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TareaDetailComponent } from './tarea-detail.component';

describe('Tarea Management Detail Component', () => {
  let comp: TareaDetailComponent;
  let fixture: ComponentFixture<TareaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TareaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tarea: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TareaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TareaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarea on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tarea).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
