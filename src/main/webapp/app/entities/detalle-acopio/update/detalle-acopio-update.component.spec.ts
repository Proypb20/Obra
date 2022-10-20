import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DetalleAcopioFormService } from './detalle-acopio-form.service';
import { DetalleAcopioService } from '../service/detalle-acopio.service';
import { IDetalleAcopio } from '../detalle-acopio.model';
import { IAcopio } from 'app/entities/acopio/acopio.model';
import { AcopioService } from 'app/entities/acopio/service/acopio.service';
import { IDetalleListaPrecio } from 'app/entities/detalle-lista-precio/detalle-lista-precio.model';
import { DetalleListaPrecioService } from 'app/entities/detalle-lista-precio/service/detalle-lista-precio.service';

import { DetalleAcopioUpdateComponent } from './detalle-acopio-update.component';

describe('DetalleAcopio Management Update Component', () => {
  let comp: DetalleAcopioUpdateComponent;
  let fixture: ComponentFixture<DetalleAcopioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detalleAcopioFormService: DetalleAcopioFormService;
  let detalleAcopioService: DetalleAcopioService;
  let acopioService: AcopioService;
  let detalleListaPrecioService: DetalleListaPrecioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DetalleAcopioUpdateComponent],
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
      .overrideTemplate(DetalleAcopioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetalleAcopioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detalleAcopioFormService = TestBed.inject(DetalleAcopioFormService);
    detalleAcopioService = TestBed.inject(DetalleAcopioService);
    acopioService = TestBed.inject(AcopioService);
    detalleListaPrecioService = TestBed.inject(DetalleListaPrecioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Acopio query and add missing value', () => {
      const detalleAcopio: IDetalleAcopio = { id: 456 };
      const acopio: IAcopio = { id: 96001 };
      detalleAcopio.acopio = acopio;

      const acopioCollection: IAcopio[] = [{ id: 81562 }];
      jest.spyOn(acopioService, 'query').mockReturnValue(of(new HttpResponse({ body: acopioCollection })));
      const additionalAcopios = [acopio];
      const expectedCollection: IAcopio[] = [...additionalAcopios, ...acopioCollection];
      jest.spyOn(acopioService, 'addAcopioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detalleAcopio });
      comp.ngOnInit();

      expect(acopioService.query).toHaveBeenCalled();
      expect(acopioService.addAcopioToCollectionIfMissing).toHaveBeenCalledWith(
        acopioCollection,
        ...additionalAcopios.map(expect.objectContaining)
      );
      expect(comp.acopiosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DetalleListaPrecio query and add missing value', () => {
      const detalleAcopio: IDetalleAcopio = { id: 456 };
      const detalleListaPrecio: IDetalleListaPrecio = { id: 9332 };
      detalleAcopio.detalleListaPrecio = detalleListaPrecio;

      const detalleListaPrecioCollection: IDetalleListaPrecio[] = [{ id: 96201 }];
      jest.spyOn(detalleListaPrecioService, 'query').mockReturnValue(of(new HttpResponse({ body: detalleListaPrecioCollection })));
      const additionalDetalleListaPrecios = [detalleListaPrecio];
      const expectedCollection: IDetalleListaPrecio[] = [...additionalDetalleListaPrecios, ...detalleListaPrecioCollection];
      jest.spyOn(detalleListaPrecioService, 'addDetalleListaPrecioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detalleAcopio });
      comp.ngOnInit();

      expect(detalleListaPrecioService.query).toHaveBeenCalled();
      expect(detalleListaPrecioService.addDetalleListaPrecioToCollectionIfMissing).toHaveBeenCalledWith(
        detalleListaPrecioCollection,
        ...additionalDetalleListaPrecios.map(expect.objectContaining)
      );
      expect(comp.detalleListaPreciosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detalleAcopio: IDetalleAcopio = { id: 456 };
      const acopio: IAcopio = { id: 92400 };
      detalleAcopio.acopio = acopio;
      const detalleListaPrecio: IDetalleListaPrecio = { id: 34337 };
      detalleAcopio.detalleListaPrecio = detalleListaPrecio;

      activatedRoute.data = of({ detalleAcopio });
      comp.ngOnInit();

      expect(comp.acopiosSharedCollection).toContain(acopio);
      expect(comp.detalleListaPreciosSharedCollection).toContain(detalleListaPrecio);
      expect(comp.detalleAcopio).toEqual(detalleAcopio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleAcopio>>();
      const detalleAcopio = { id: 123 };
      jest.spyOn(detalleAcopioFormService, 'getDetalleAcopio').mockReturnValue(detalleAcopio);
      jest.spyOn(detalleAcopioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleAcopio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalleAcopio }));
      saveSubject.complete();

      // THEN
      expect(detalleAcopioFormService.getDetalleAcopio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(detalleAcopioService.update).toHaveBeenCalledWith(expect.objectContaining(detalleAcopio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleAcopio>>();
      const detalleAcopio = { id: 123 };
      jest.spyOn(detalleAcopioFormService, 'getDetalleAcopio').mockReturnValue({ id: null });
      jest.spyOn(detalleAcopioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleAcopio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalleAcopio }));
      saveSubject.complete();

      // THEN
      expect(detalleAcopioFormService.getDetalleAcopio).toHaveBeenCalled();
      expect(detalleAcopioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleAcopio>>();
      const detalleAcopio = { id: 123 };
      jest.spyOn(detalleAcopioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleAcopio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detalleAcopioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAcopio', () => {
      it('Should forward to acopioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(acopioService, 'compareAcopio');
        comp.compareAcopio(entity, entity2);
        expect(acopioService.compareAcopio).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDetalleListaPrecio', () => {
      it('Should forward to detalleListaPrecioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(detalleListaPrecioService, 'compareDetalleListaPrecio');
        comp.compareDetalleListaPrecio(entity, entity2);
        expect(detalleListaPrecioService.compareDetalleListaPrecio).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
