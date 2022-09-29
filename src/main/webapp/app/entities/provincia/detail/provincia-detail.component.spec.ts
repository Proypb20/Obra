import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvinciaDetailComponent } from './provincia-detail.component';

describe('Provincia Management Detail Component', () => {
  let comp: ProvinciaDetailComponent;
  let fixture: ComponentFixture<ProvinciaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProvinciaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ provincia: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProvinciaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProvinciaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load provincia on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.provincia).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
