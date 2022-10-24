import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MovimientoFormService } from './movimiento-form.service';
import { MovimientoService } from '../service/movimiento.service';
import { IMovimiento } from '../movimiento.model';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SubcontratistaService } from 'app/entities/subcontratista/service/subcontratista.service';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { ConceptoService } from 'app/entities/concepto/service/concepto.service';
import { ITipoComprobante } from 'app/entities/tipo-comprobante/tipo-comprobante.model';
import { TipoComprobanteService } from 'app/entities/tipo-comprobante/service/tipo-comprobante.service';

import { MovimientoUpdateComponent } from './movimiento-update.component';

describe('Movimiento Management Update Component', () => {
  let comp: MovimientoUpdateComponent;
  let fixture: ComponentFixture<MovimientoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let movimientoFormService: MovimientoFormService;
  let movimientoService: MovimientoService;
  let obraService: ObraService;
  let subcontratistaService: SubcontratistaService;
  let conceptoService: ConceptoService;
  let tipoComprobanteService: TipoComprobanteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MovimientoUpdateComponent],
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
      .overrideTemplate(MovimientoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MovimientoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    movimientoFormService = TestBed.inject(MovimientoFormService);
    movimientoService = TestBed.inject(MovimientoService);
    obraService = TestBed.inject(ObraService);
    subcontratistaService = TestBed.inject(SubcontratistaService);
    conceptoService = TestBed.inject(ConceptoService);
    tipoComprobanteService = TestBed.inject(TipoComprobanteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Obra query and add missing value', () => {
      const movimiento: IMovimiento = { id: 456 };
      const obra: IObra = { id: 63992 };
      movimiento.obra = obra;

      const obraCollection: IObra[] = [{ id: 75918 }];
      jest.spyOn(obraService, 'query').mockReturnValue(of(new HttpResponse({ body: obraCollection })));
      const additionalObras = [obra];
      const expectedCollection: IObra[] = [...additionalObras, ...obraCollection];
      jest.spyOn(obraService, 'addObraToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ movimiento });
      comp.ngOnInit();

      expect(obraService.query).toHaveBeenCalled();
      expect(obraService.addObraToCollectionIfMissing).toHaveBeenCalledWith(
        obraCollection,
        ...additionalObras.map(expect.objectContaining)
      );
      expect(comp.obrasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Subcontratista query and add missing value', () => {
      const movimiento: IMovimiento = { id: 456 };
      const subcontratista: ISubcontratista = { id: 44240 };
      movimiento.subcontratista = subcontratista;

      const subcontratistaCollection: ISubcontratista[] = [{ id: 6731 }];
      jest.spyOn(subcontratistaService, 'query').mockReturnValue(of(new HttpResponse({ body: subcontratistaCollection })));
      const additionalSubcontratistas = [subcontratista];
      const expectedCollection: ISubcontratista[] = [...additionalSubcontratistas, ...subcontratistaCollection];
      jest.spyOn(subcontratistaService, 'addSubcontratistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ movimiento });
      comp.ngOnInit();

      expect(subcontratistaService.query).toHaveBeenCalled();
      expect(subcontratistaService.addSubcontratistaToCollectionIfMissing).toHaveBeenCalledWith(
        subcontratistaCollection,
        ...additionalSubcontratistas.map(expect.objectContaining)
      );
      expect(comp.subcontratistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Concepto query and add missing value', () => {
      const movimiento: IMovimiento = { id: 456 };
      const concepto: IConcepto = { id: 42462 };
      movimiento.concepto = concepto;

      const conceptoCollection: IConcepto[] = [{ id: 43921 }];
      jest.spyOn(conceptoService, 'query').mockReturnValue(of(new HttpResponse({ body: conceptoCollection })));
      const additionalConceptos = [concepto];
      const expectedCollection: IConcepto[] = [...additionalConceptos, ...conceptoCollection];
      jest.spyOn(conceptoService, 'addConceptoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ movimiento });
      comp.ngOnInit();

      expect(conceptoService.query).toHaveBeenCalled();
      expect(conceptoService.addConceptoToCollectionIfMissing).toHaveBeenCalledWith(
        conceptoCollection,
        ...additionalConceptos.map(expect.objectContaining)
      );
      expect(comp.conceptosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TipoComprobante query and add missing value', () => {
      const movimiento: IMovimiento = { id: 456 };
      const tipoComprobante: ITipoComprobante = { id: 25097 };
      movimiento.tipoComprobante = tipoComprobante;

      const tipoComprobanteCollection: ITipoComprobante[] = [{ id: 89251 }];
      jest.spyOn(tipoComprobanteService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoComprobanteCollection })));
      const additionalTipoComprobantes = [tipoComprobante];
      const expectedCollection: ITipoComprobante[] = [...additionalTipoComprobantes, ...tipoComprobanteCollection];
      jest.spyOn(tipoComprobanteService, 'addTipoComprobanteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ movimiento });
      comp.ngOnInit();

      expect(tipoComprobanteService.query).toHaveBeenCalled();
      expect(tipoComprobanteService.addTipoComprobanteToCollectionIfMissing).toHaveBeenCalledWith(
        tipoComprobanteCollection,
        ...additionalTipoComprobantes.map(expect.objectContaining)
      );
      expect(comp.tipoComprobantesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const movimiento: IMovimiento = { id: 456 };
      const obra: IObra = { id: 62647 };
      movimiento.obra = obra;
      const subcontratista: ISubcontratista = { id: 67201 };
      movimiento.subcontratista = subcontratista;
      const concepto: IConcepto = { id: 99305 };
      movimiento.concepto = concepto;
      const tipoComprobante: ITipoComprobante = { id: 32740 };
      movimiento.tipoComprobante = tipoComprobante;

      activatedRoute.data = of({ movimiento });
      comp.ngOnInit();

      expect(comp.obrasSharedCollection).toContain(obra);
      expect(comp.subcontratistasSharedCollection).toContain(subcontratista);
      expect(comp.conceptosSharedCollection).toContain(concepto);
      expect(comp.tipoComprobantesSharedCollection).toContain(tipoComprobante);
      expect(comp.movimiento).toEqual(movimiento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMovimiento>>();
      const movimiento = { id: 123 };
      jest.spyOn(movimientoFormService, 'getMovimiento').mockReturnValue(movimiento);
      jest.spyOn(movimientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ movimiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: movimiento }));
      saveSubject.complete();

      // THEN
      expect(movimientoFormService.getMovimiento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(movimientoService.update).toHaveBeenCalledWith(expect.objectContaining(movimiento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMovimiento>>();
      const movimiento = { id: 123 };
      jest.spyOn(movimientoFormService, 'getMovimiento').mockReturnValue({ id: null });
      jest.spyOn(movimientoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ movimiento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: movimiento }));
      saveSubject.complete();

      // THEN
      expect(movimientoFormService.getMovimiento).toHaveBeenCalled();
      expect(movimientoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMovimiento>>();
      const movimiento = { id: 123 };
      jest.spyOn(movimientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ movimiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(movimientoService.update).toHaveBeenCalled();
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

    describe('compareTipoComprobante', () => {
      it('Should forward to tipoComprobanteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tipoComprobanteService, 'compareTipoComprobante');
        comp.compareTipoComprobante(entity, entity2);
        expect(tipoComprobanteService.compareTipoComprobante).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
