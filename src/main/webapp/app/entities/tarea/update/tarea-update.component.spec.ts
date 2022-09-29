import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TareaFormService } from './tarea-form.service';
import { TareaService } from '../service/tarea.service';
import { ITarea } from '../tarea.model';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SubcontratistaService } from 'app/entities/subcontratista/service/subcontratista.service';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { ConceptoService } from 'app/entities/concepto/service/concepto.service';

import { TareaUpdateComponent } from './tarea-update.component';

describe('Tarea Management Update Component', () => {
  let comp: TareaUpdateComponent;
  let fixture: ComponentFixture<TareaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tareaFormService: TareaFormService;
  let tareaService: TareaService;
  let obraService: ObraService;
  let subcontratistaService: SubcontratistaService;
  let conceptoService: ConceptoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TareaUpdateComponent],
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
      .overrideTemplate(TareaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TareaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tareaFormService = TestBed.inject(TareaFormService);
    tareaService = TestBed.inject(TareaService);
    obraService = TestBed.inject(ObraService);
    subcontratistaService = TestBed.inject(SubcontratistaService);
    conceptoService = TestBed.inject(ConceptoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Obra query and add missing value', () => {
      const tarea: ITarea = { id: 456 };
      const obra: IObra = { id: 76207 };
      tarea.obra = obra;

      const obraCollection: IObra[] = [{ id: 46735 }];
      jest.spyOn(obraService, 'query').mockReturnValue(of(new HttpResponse({ body: obraCollection })));
      const additionalObras = [obra];
      const expectedCollection: IObra[] = [...additionalObras, ...obraCollection];
      jest.spyOn(obraService, 'addObraToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarea });
      comp.ngOnInit();

      expect(obraService.query).toHaveBeenCalled();
      expect(obraService.addObraToCollectionIfMissing).toHaveBeenCalledWith(
        obraCollection,
        ...additionalObras.map(expect.objectContaining)
      );
      expect(comp.obrasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Subcontratista query and add missing value', () => {
      const tarea: ITarea = { id: 456 };
      const subcontratista: ISubcontratista = { id: 19687 };
      tarea.subcontratista = subcontratista;

      const subcontratistaCollection: ISubcontratista[] = [{ id: 35706 }];
      jest.spyOn(subcontratistaService, 'query').mockReturnValue(of(new HttpResponse({ body: subcontratistaCollection })));
      const additionalSubcontratistas = [subcontratista];
      const expectedCollection: ISubcontratista[] = [...additionalSubcontratistas, ...subcontratistaCollection];
      jest.spyOn(subcontratistaService, 'addSubcontratistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarea });
      comp.ngOnInit();

      expect(subcontratistaService.query).toHaveBeenCalled();
      expect(subcontratistaService.addSubcontratistaToCollectionIfMissing).toHaveBeenCalledWith(
        subcontratistaCollection,
        ...additionalSubcontratistas.map(expect.objectContaining)
      );
      expect(comp.subcontratistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Concepto query and add missing value', () => {
      const tarea: ITarea = { id: 456 };
      const concepto: IConcepto = { id: 2179 };
      tarea.concepto = concepto;

      const conceptoCollection: IConcepto[] = [{ id: 78746 }];
      jest.spyOn(conceptoService, 'query').mockReturnValue(of(new HttpResponse({ body: conceptoCollection })));
      const additionalConceptos = [concepto];
      const expectedCollection: IConcepto[] = [...additionalConceptos, ...conceptoCollection];
      jest.spyOn(conceptoService, 'addConceptoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarea });
      comp.ngOnInit();

      expect(conceptoService.query).toHaveBeenCalled();
      expect(conceptoService.addConceptoToCollectionIfMissing).toHaveBeenCalledWith(
        conceptoCollection,
        ...additionalConceptos.map(expect.objectContaining)
      );
      expect(comp.conceptosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarea: ITarea = { id: 456 };
      const obra: IObra = { id: 71202 };
      tarea.obra = obra;
      const subcontratista: ISubcontratista = { id: 23426 };
      tarea.subcontratista = subcontratista;
      const concepto: IConcepto = { id: 65063 };
      tarea.concepto = concepto;

      activatedRoute.data = of({ tarea });
      comp.ngOnInit();

      expect(comp.obrasSharedCollection).toContain(obra);
      expect(comp.subcontratistasSharedCollection).toContain(subcontratista);
      expect(comp.conceptosSharedCollection).toContain(concepto);
      expect(comp.tarea).toEqual(tarea);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarea>>();
      const tarea = { id: 123 };
      jest.spyOn(tareaFormService, 'getTarea').mockReturnValue(tarea);
      jest.spyOn(tareaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarea }));
      saveSubject.complete();

      // THEN
      expect(tareaFormService.getTarea).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tareaService.update).toHaveBeenCalledWith(expect.objectContaining(tarea));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarea>>();
      const tarea = { id: 123 };
      jest.spyOn(tareaFormService, 'getTarea').mockReturnValue({ id: null });
      jest.spyOn(tareaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarea: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarea }));
      saveSubject.complete();

      // THEN
      expect(tareaFormService.getTarea).toHaveBeenCalled();
      expect(tareaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarea>>();
      const tarea = { id: 123 };
      jest.spyOn(tareaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tareaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareObra', () => {
      it('Should forward to obraService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(obraService, 'compareObra');
        comp.compareObra(entity, entity2);
        expect(obraService.compareObra).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSubcontratista', () => {
      it('Should forward to subcontratistaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(subcontratistaService, 'compareSubcontratista');
        comp.compareSubcontratista(entity, entity2);
        expect(subcontratistaService.compareSubcontratista).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareConcepto', () => {
      it('Should forward to conceptoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(conceptoService, 'compareConcepto');
        comp.compareConcepto(entity, entity2);
        expect(conceptoService.compareConcepto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
