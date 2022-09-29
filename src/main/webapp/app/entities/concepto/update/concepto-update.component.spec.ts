import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConceptoFormService } from './concepto-form.service';
import { ConceptoService } from '../service/concepto.service';
import { IConcepto } from '../concepto.model';

import { ConceptoUpdateComponent } from './concepto-update.component';

describe('Concepto Management Update Component', () => {
  let comp: ConceptoUpdateComponent;
  let fixture: ComponentFixture<ConceptoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let conceptoFormService: ConceptoFormService;
  let conceptoService: ConceptoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConceptoUpdateComponent],
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
      .overrideTemplate(ConceptoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConceptoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    conceptoFormService = TestBed.inject(ConceptoFormService);
    conceptoService = TestBed.inject(ConceptoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const concepto: IConcepto = { id: 456 };

      activatedRoute.data = of({ concepto });
      comp.ngOnInit();

      expect(comp.concepto).toEqual(concepto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConcepto>>();
      const concepto = { id: 123 };
      jest.spyOn(conceptoFormService, 'getConcepto').mockReturnValue(concepto);
      jest.spyOn(conceptoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concepto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: concepto }));
      saveSubject.complete();

      // THEN
      expect(conceptoFormService.getConcepto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(conceptoService.update).toHaveBeenCalledWith(expect.objectContaining(concepto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConcepto>>();
      const concepto = { id: 123 };
      jest.spyOn(conceptoFormService, 'getConcepto').mockReturnValue({ id: null });
      jest.spyOn(conceptoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concepto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: concepto }));
      saveSubject.complete();

      // THEN
      expect(conceptoFormService.getConcepto).toHaveBeenCalled();
      expect(conceptoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConcepto>>();
      const concepto = { id: 123 };
      jest.spyOn(conceptoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concepto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(conceptoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
