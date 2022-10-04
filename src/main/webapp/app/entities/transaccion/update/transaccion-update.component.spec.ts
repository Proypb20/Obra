import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransaccionFormService } from './transaccion-form.service';
import { TransaccionService } from '../service/transaccion.service';
import { ITransaccion } from '../transaccion.model';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SubcontratistaService } from 'app/entities/subcontratista/service/subcontratista.service';
import { ITipoComprobante } from 'app/entities/tipo-comprobante/tipo-comprobante.model';
import { TipoComprobanteService } from 'app/entities/tipo-comprobante/service/tipo-comprobante.service';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { ConceptoService } from 'app/entities/concepto/service/concepto.service';

import { TransaccionUpdateComponent } from './transaccion-update.component';

describe('Transaccion Management Update Component', () => {
  let comp: TransaccionUpdateComponent;
  let fixture: ComponentFixture<TransaccionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transaccionFormService: TransaccionFormService;
  let transaccionService: TransaccionService;
  let obraService: ObraService;
  let subcontratistaService: SubcontratistaService;
  let tipoComprobanteService: TipoComprobanteService;
  let conceptoService: ConceptoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransaccionUpdateComponent],
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
      .overrideTemplate(TransaccionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransaccionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transaccionFormService = TestBed.inject(TransaccionFormService);
    transaccionService = TestBed.inject(TransaccionService);
    obraService = TestBed.inject(ObraService);
    subcontratistaService = TestBed.inject(SubcontratistaService);
    tipoComprobanteService = TestBed.inject(TipoComprobanteService);
    conceptoService = TestBed.inject(ConceptoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Obra query and add missing value', () => {
      const transaccion: ITransaccion = { id: 456 };
      const obra: IObra = { id: 40474 };
      transaccion.obra = obra;

      const obraCollection: IObra[] = [{ id: 61719 }];
      jest.spyOn(obraService, 'query').mockReturnValue(of(new HttpResponse({ body: obraCollection })));
      const additionalObras = [obra];
      const expectedCollection: IObra[] = [...additionalObras, ...obraCollection];
      jest.spyOn(obraService, 'addObraToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transaccion });
      comp.ngOnInit();

      expect(obraService.query).toHaveBeenCalled();
      expect(obraService.addObraToCollectionIfMissing).toHaveBeenCalledWith(
        obraCollection,
        ...additionalObras.map(expect.objectContaining)
      );
      expect(comp.obrasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Subcontratista query and add missing value', () => {
      const transaccion: ITransaccion = { id: 456 };
      const subcontratista: ISubcontratista = { id: 95363 };
      transaccion.subcontratista = subcontratista;

      const subcontratistaCollection: ISubcontratista[] = [{ id: 93680 }];
      jest.spyOn(subcontratistaService, 'query').mockReturnValue(of(new HttpResponse({ body: subcontratistaCollection })));
      const additionalSubcontratistas = [subcontratista];
      const expectedCollection: ISubcontratista[] = [...additionalSubcontratistas, ...subcontratistaCollection];
      jest.spyOn(subcontratistaService, 'addSubcontratistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transaccion });
      comp.ngOnInit();

      expect(subcontratistaService.query).toHaveBeenCalled();
      expect(subcontratistaService.addSubcontratistaToCollectionIfMissing).toHaveBeenCalledWith(
        subcontratistaCollection,
        ...additionalSubcontratistas.map(expect.objectContaining)
      );
      expect(comp.subcontratistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TipoComprobante query and add missing value', () => {
      const transaccion: ITransaccion = { id: 456 };
      const tipoComprobante: ITipoComprobante = { id: 20820 };
      transaccion.tipoComprobante = tipoComprobante;

      const tipoComprobanteCollection: ITipoComprobante[] = [{ id: 85950 }];
      jest.spyOn(tipoComprobanteService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoComprobanteCollection })));
      const additionalTipoComprobantes = [tipoComprobante];
      const expectedCollection: ITipoComprobante[] = [...additionalTipoComprobantes, ...tipoComprobanteCollection];
      jest.spyOn(tipoComprobanteService, 'addTipoComprobanteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transaccion });
      comp.ngOnInit();

      expect(tipoComprobanteService.query).toHaveBeenCalled();
      expect(tipoComprobanteService.addTipoComprobanteToCollectionIfMissing).toHaveBeenCalledWith(
        tipoComprobanteCollection,
        ...additionalTipoComprobantes.map(expect.objectContaining)
      );
      expect(comp.tipoComprobantesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Concepto query and add missing value', () => {
      const transaccion: ITransaccion = { id: 456 };
      const concepto: IConcepto = { id: 58409 };
      transaccion.concepto = concepto;

      const conceptoCollection: IConcepto[] = [{ id: 85653 }];
      jest.spyOn(conceptoService, 'query').mockReturnValue(of(new HttpResponse({ body: conceptoCollection })));
      const additionalConceptos = [concepto];
      const expectedCollection: IConcepto[] = [...additionalConceptos, ...conceptoCollection];
      jest.spyOn(conceptoService, 'addConceptoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transaccion });
      comp.ngOnInit();

      expect(conceptoService.query).toHaveBeenCalled();
      expect(conceptoService.addConceptoToCollectionIfMissing).toHaveBeenCalledWith(
        conceptoCollection,
        ...additionalConceptos.map(expect.objectContaining)
      );
      expect(comp.conceptosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transaccion: ITransaccion = { id: 456 };
      const obra: IObra = { id: 50960 };
      transaccion.obra = obra;
      const subcontratista: ISubcontratista = { id: 96441 };
      transaccion.subcontratista = subcontratista;
      const tipoComprobante: ITipoComprobante = { id: 11238 };
      transaccion.tipoComprobante = tipoComprobante;
      const concepto: IConcepto = { id: 99116 };
      transaccion.concepto = concepto;

      activatedRoute.data = of({ transaccion });
      comp.ngOnInit();

      expect(comp.obrasSharedCollection).toContain(obra);
      expect(comp.subcontratistasSharedCollection).toContain(subcontratista);
      expect(comp.tipoComprobantesSharedCollection).toContain(tipoComprobante);
      expect(comp.conceptosSharedCollection).toContain(concepto);
      expect(comp.transaccion).toEqual(transaccion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransaccion>>();
      const transaccion = { id: 123 };
      jest.spyOn(transaccionFormService, 'getTransaccion').mockReturnValue(transaccion);
      jest.spyOn(transaccionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaccion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transaccion }));
      saveSubject.complete();

      // THEN
      expect(transaccionFormService.getTransaccion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(transaccionService.update).toHaveBeenCalledWith(expect.objectContaining(transaccion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransaccion>>();
      const transaccion = { id: 123 };
      jest.spyOn(transaccionFormService, 'getTransaccion').mockReturnValue({ id: null });
      jest.spyOn(transaccionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaccion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transaccion }));
      saveSubject.complete();

      // THEN
      expect(transaccionFormService.getTransaccion).toHaveBeenCalled();
      expect(transaccionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransaccion>>();
      const transaccion = { id: 123 };
      jest.spyOn(transaccionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaccion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transaccionService.update).toHaveBeenCalled();
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

    describe('compareTipoComprobante', () => {
      it('Should forward to tipoComprobanteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tipoComprobanteService, 'compareTipoComprobante');
        comp.compareTipoComprobante(entity, entity2);
        expect(tipoComprobanteService.compareTipoComprobante).toHaveBeenCalledWith(entity, entity2);
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
