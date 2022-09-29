import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProvinciaFormService } from './provincia-form.service';
import { ProvinciaService } from '../service/provincia.service';
import { IProvincia } from '../provincia.model';

import { ProvinciaUpdateComponent } from './provincia-update.component';

describe('Provincia Management Update Component', () => {
  let comp: ProvinciaUpdateComponent;
  let fixture: ComponentFixture<ProvinciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let provinciaFormService: ProvinciaFormService;
  let provinciaService: ProvinciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProvinciaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProvinciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProvinciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    provinciaFormService = TestBed.inject(ProvinciaFormService);
    provinciaService = TestBed.inject(ProvinciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const provincia: IProvincia = { id: 456 };

      activatedRoute.data = of({ provincia });
      comp.ngOnInit();

      expect(comp.provincia).toEqual(provincia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvincia>>();
      const provincia = { id: 123 };
      jest.spyOn(provinciaFormService, 'getProvincia').mockReturnValue(provincia);
      jest.spyOn(provinciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provincia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: provincia }));
      saveSubject.complete();

      // THEN
      expect(provinciaFormService.getProvincia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(provinciaService.update).toHaveBeenCalledWith(expect.objectContaining(provincia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvincia>>();
      const provincia = { id: 123 };
      jest.spyOn(provinciaFormService, 'getProvincia').mockReturnValue({ id: null });
      jest.spyOn(provinciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provincia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: provincia }));
      saveSubject.complete();

      // THEN
      expect(provinciaFormService.getProvincia).toHaveBeenCalled();
      expect(provinciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvincia>>();
      const provincia = { id: 123 };
      jest.spyOn(provinciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provincia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(provinciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
