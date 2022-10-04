import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SubcontratistaFormService } from './subcontratista-form.service';
import { SubcontratistaService } from '../service/subcontratista.service';
import { ISubcontratista } from '../subcontratista.model';

import { SubcontratistaUpdateComponent } from './subcontratista-update.component';

describe('Subcontratista Management Update Component', () => {
  let comp: SubcontratistaUpdateComponent;
  let fixture: ComponentFixture<SubcontratistaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subcontratistaFormService: SubcontratistaFormService;
  let subcontratistaService: SubcontratistaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SubcontratistaUpdateComponent],
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
      .overrideTemplate(SubcontratistaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubcontratistaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subcontratistaFormService = TestBed.inject(SubcontratistaFormService);
    subcontratistaService = TestBed.inject(SubcontratistaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const subcontratista: ISubcontratista = { id: 456 };

      activatedRoute.data = of({ subcontratista });
      comp.ngOnInit();

      expect(comp.subcontratista).toEqual(subcontratista);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubcontratista>>();
      const subcontratista = { id: 123 };
      jest.spyOn(subcontratistaFormService, 'getSubcontratista').mockReturnValue(subcontratista);
      jest.spyOn(subcontratistaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subcontratista });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subcontratista }));
      saveSubject.complete();

      // THEN
      expect(subcontratistaFormService.getSubcontratista).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(subcontratistaService.update).toHaveBeenCalledWith(expect.objectContaining(subcontratista));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubcontratista>>();
      const subcontratista = { id: 123 };
      jest.spyOn(subcontratistaFormService, 'getSubcontratista').mockReturnValue({ id: null });
      jest.spyOn(subcontratistaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subcontratista: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subcontratista }));
      saveSubject.complete();

      // THEN
      expect(subcontratistaFormService.getSubcontratista).toHaveBeenCalled();
      expect(subcontratistaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubcontratista>>();
      const subcontratista = { id: 123 };
      jest.spyOn(subcontratistaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subcontratista });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subcontratistaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
