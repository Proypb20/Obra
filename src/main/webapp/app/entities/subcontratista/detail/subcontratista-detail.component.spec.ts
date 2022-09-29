import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubcontratistaDetailComponent } from './subcontratista-detail.component';

describe('Subcontratista Management Detail Component', () => {
  let comp: SubcontratistaDetailComponent;
  let fixture: ComponentFixture<SubcontratistaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubcontratistaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subcontratista: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SubcontratistaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubcontratistaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subcontratista on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subcontratista).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
