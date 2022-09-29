import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ListaPrecioFormService } from './lista-precio-form.service';
import { ListaPrecioService } from '../service/lista-precio.service';
import { IListaPrecio } from '../lista-precio.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

import { ListaPrecioUpdateComponent } from './lista-precio-update.component';

describe('ListaPrecio Management Update Component', () => {
  let comp: ListaPrecioUpdateComponent;
  let fixture: ComponentFixture<ListaPrecioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let listaPrecioFormService: ListaPrecioFormService;
  let listaPrecioService: ListaPrecioService;
  let proveedorService: ProveedorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ListaPrecioUpdateComponent],
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
      .overrideTemplate(ListaPrecioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ListaPrecioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    listaPrecioFormService = TestBed.inject(ListaPrecioFormService);
    listaPrecioService = TestBed.inject(ListaPrecioService);
    proveedorService = TestBed.inject(ProveedorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Proveedor query and add missing value', () => {
      const listaPrecio: IListaPrecio = { id: 456 };
      const proveedor: IProveedor = { id: 16089 };
      listaPrecio.proveedor = proveedor;

      const proveedorCollection: IProveedor[] = [{ id: 83143 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [proveedor];
      const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
      jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ listaPrecio });
      comp.ngOnInit();

      expect(proveedorService.query).toHaveBeenCalled();
      expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(
        proveedorCollection,
        ...additionalProveedors.map(expect.objectContaining)
      );
      expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const listaPrecio: IListaPrecio = { id: 456 };
      const proveedor: IProveedor = { id: 88528 };
      listaPrecio.proveedor = proveedor;

      activatedRoute.data = of({ listaPrecio });
      comp.ngOnInit();

      expect(comp.proveedorsSharedCollection).toContain(proveedor);
      expect(comp.listaPrecio).toEqual(listaPrecio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IListaPrecio>>();
      const listaPrecio = { id: 123 };
      jest.spyOn(listaPrecioFormService, 'getListaPrecio').mockReturnValue(listaPrecio);
      jest.spyOn(listaPrecioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ listaPrecio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: listaPrecio }));
      saveSubject.complete();

      // THEN
      expect(listaPrecioFormService.getListaPrecio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(listaPrecioService.update).toHaveBeenCalledWith(expect.objectContaining(listaPrecio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IListaPrecio>>();
      const listaPrecio = { id: 123 };
      jest.spyOn(listaPrecioFormService, 'getListaPrecio').mockReturnValue({ id: null });
      jest.spyOn(listaPrecioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ listaPrecio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: listaPrecio }));
      saveSubject.complete();

      // THEN
      expect(listaPrecioFormService.getListaPrecio).toHaveBeenCalled();
      expect(listaPrecioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IListaPrecio>>();
      const listaPrecio = { id: 123 };
      jest.spyOn(listaPrecioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ listaPrecio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(listaPrecioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
