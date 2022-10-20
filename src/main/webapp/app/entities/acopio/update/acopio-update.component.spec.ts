import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AcopioFormService } from './acopio-form.service';
import { AcopioService } from '../service/acopio.service';
import { IAcopio } from '../acopio.model';
import { IObra } from 'app/entities/obra/obra.model';
import { ObraService } from 'app/entities/obra/service/obra.service';
import { IListaPrecio } from 'app/entities/lista-precio/lista-precio.model';
import { ListaPrecioService } from 'app/entities/lista-precio/service/lista-precio.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

import { AcopioUpdateComponent } from './acopio-update.component';

describe('Acopio Management Update Component', () => {
  let comp: AcopioUpdateComponent;
  let fixture: ComponentFixture<AcopioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let acopioFormService: AcopioFormService;
  let acopioService: AcopioService;
  let obraService: ObraService;
  let listaPrecioService: ListaPrecioService;
  let proveedorService: ProveedorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AcopioUpdateComponent],
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
      .overrideTemplate(AcopioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AcopioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    acopioFormService = TestBed.inject(AcopioFormService);
    acopioService = TestBed.inject(AcopioService);
    obraService = TestBed.inject(ObraService);
    listaPrecioService = TestBed.inject(ListaPrecioService);
    proveedorService = TestBed.inject(ProveedorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Obra query and add missing value', () => {
      const acopio: IAcopio = { id: 456 };
      const obra: IObra = { id: 97316 };
      acopio.obra = obra;

      const obraCollection: IObra[] = [{ id: 57829 }];
      jest.spyOn(obraService, 'query').mockReturnValue(of(new HttpResponse({ body: obraCollection })));
      const additionalObras = [obra];
      const expectedCollection: IObra[] = [...additionalObras, ...obraCollection];
      jest.spyOn(obraService, 'addObraToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ acopio });
      comp.ngOnInit();

      expect(obraService.query).toHaveBeenCalled();
      expect(obraService.addObraToCollectionIfMissing).toHaveBeenCalledWith(
        obraCollection,
        ...additionalObras.map(expect.objectContaining)
      );
      expect(comp.obrasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ListaPrecio query and add missing value', () => {
      const acopio: IAcopio = { id: 456 };
      const listaprecio: IListaPrecio = { id: 67011 };
      acopio.listaprecio = listaprecio;

      const listaPrecioCollection: IListaPrecio[] = [{ id: 18256 }];
      jest.spyOn(listaPrecioService, 'query').mockReturnValue(of(new HttpResponse({ body: listaPrecioCollection })));
      const additionalListaPrecios = [listaprecio];
      const expectedCollection: IListaPrecio[] = [...additionalListaPrecios, ...listaPrecioCollection];
      jest.spyOn(listaPrecioService, 'addListaPrecioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ acopio });
      comp.ngOnInit();

      expect(listaPrecioService.query).toHaveBeenCalled();
      expect(listaPrecioService.addListaPrecioToCollectionIfMissing).toHaveBeenCalledWith(
        listaPrecioCollection,
        ...additionalListaPrecios.map(expect.objectContaining)
      );
      expect(comp.listaPreciosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Proveedor query and add missing value', () => {
      const acopio: IAcopio = { id: 456 };
      const proveedor: IProveedor = { id: 53765 };
      acopio.proveedor = proveedor;

      const proveedorCollection: IProveedor[] = [{ id: 78723 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [proveedor];
      const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
      jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ acopio });
      comp.ngOnInit();

      expect(proveedorService.query).toHaveBeenCalled();
      expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(
        proveedorCollection,
        ...additionalProveedors.map(expect.objectContaining)
      );
      expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const acopio: IAcopio = { id: 456 };
      const obra: IObra = { id: 19427 };
      acopio.obra = obra;
      const listaprecio: IListaPrecio = { id: 39295 };
      acopio.listaprecio = listaprecio;
      const proveedor: IProveedor = { id: 71796 };
      acopio.proveedor = proveedor;

      activatedRoute.data = of({ acopio });
      comp.ngOnInit();

      expect(comp.obrasSharedCollection).toContain(obra);
      expect(comp.listaPreciosSharedCollection).toContain(listaprecio);
      expect(comp.proveedorsSharedCollection).toContain(proveedor);
      expect(comp.acopio).toEqual(acopio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAcopio>>();
      const acopio = { id: 123 };
      jest.spyOn(acopioFormService, 'getAcopio').mockReturnValue(acopio);
      jest.spyOn(acopioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ acopio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: acopio }));
      saveSubject.complete();

      // THEN
      expect(acopioFormService.getAcopio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(acopioService.update).toHaveBeenCalledWith(expect.objectContaining(acopio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAcopio>>();
      const acopio = { id: 123 };
      jest.spyOn(acopioFormService, 'getAcopio').mockReturnValue({ id: null });
      jest.spyOn(acopioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ acopio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: acopio }));
      saveSubject.complete();

      // THEN
      expect(acopioFormService.getAcopio).toHaveBeenCalled();
      expect(acopioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAcopio>>();
      const acopio = { id: 123 };
      jest.spyOn(acopioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ acopio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(acopioService.update).toHaveBeenCalled();
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

    describe('compareListaPrecio', () => {
      it('Should forward to listaPrecioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(listaPrecioService, 'compareListaPrecio');
        comp.compareListaPrecio(entity, entity2);
        expect(listaPrecioService.compareListaPrecio).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProveedor', () => {
      it('Should forward to proveedorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(proveedorService, 'compareProveedor');
        comp.compareProveedor(entity, entity2);
        expect(proveedorService.compareProveedor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
