import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransaccionDetailComponent } from './transaccion-detail.component';

describe('Transaccion Management Detail Component', () => {
  let comp: TransaccionDetailComponent;
  let fixture: ComponentFixture<TransaccionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransaccionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transaccion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransaccionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransaccionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transaccion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transaccion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
