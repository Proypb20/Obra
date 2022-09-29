import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DetalleListaPrecioFormService } from './detalle-lista-precio-form.service';
import { DetalleListaPrecioService } from '../service/detalle-lista-precio.service';
import { IDetalleListaPrecio } from '../detalle-lista-precio.model';
import { IListaPrecio } from 'app/entities/lista-precio/lista-precio.model';
import { ListaPrecioService } from 'app/entities/lista-precio/service/lista-precio.service';

import { DetalleListaPrecioUpdateComponent } from './detalle-lista-precio-update.component';

describe('DetalleListaPrecio Management Update Component', () => {
  let comp: DetalleListaPrecioUpdateComponent;
  let fixture: ComponentFixture<DetalleListaPrecioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detalleListaPrecioFormService: DetalleListaPrecioFormService;
  let detalleListaPrecioService: DetalleListaPrecioService;
  let listaPrecioService: ListaPrecioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DetalleListaPrecioUpdateComponent],
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
      .overrideTemplate(DetalleListaPrecioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetalleListaPrecioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detalleListaPrecioFormService = TestBed.inject(DetalleListaPrecioFormService);
    detalleListaPrecioService = TestBed.inject(DetalleListaPrecioService);
    listaPrecioService = TestBed.inject(ListaPrecioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ListaPrecio query and add missing value', () => {
      const detalleListaPrecio: IDetalleListaPrecio = { id: 456 };
      const listaPrecio: IListaPrecio = { id: 63146 };
      detalleListaPrecio.listaPrecio = listaPrecio;

      const listaPrecioCollection: IListaPrecio[] = [{ id: 26844 }];
      jest.spyOn(listaPrecioService, 'query').mockReturnValue(of(new HttpResponse({ body: listaPrecioCollection })));
      const additionalListaPrecios = [listaPrecio];
      const expectedCollection: IListaPrecio[] = [...additionalListaPrecios, ...listaPrecioCollection];
      jest.spyOn(listaPrecioService, 'addListaPrecioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detalleListaPrecio });
      comp.ngOnInit();

      expect(listaPrecioService.query).toHaveBeenCalled();
      expect(listaPrecioService.addListaPrecioToCollectionIfMissing).toHaveBeenCalledWith(
        listaPrecioCollection,
        ...additionalListaPrecios.map(expect.objectContaining)
      );
      expect(comp.listaPreciosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detalleListaPrecio: IDetalleListaPrecio = { id: 456 };
      const listaPrecio: IListaPrecio = { id: 6591 };
      detalleListaPrecio.listaPrecio = listaPrecio;

      activatedRoute.data = of({ detalleListaPrecio });
      comp.ngOnInit();

      expect(comp.listaPreciosSharedCollection).toContain(listaPrecio);
      expect(comp.detalleListaPrecio).toEqual(detalleListaPrecio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleListaPrecio>>();
      const detalleListaPrecio = { id: 123 };
      jest.spyOn(detalleListaPrecioFormService, 'getDetalleListaPrecio').mockReturnValue(detalleListaPrecio);
      jest.spyOn(detalleListaPrecioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleListaPrecio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalleListaPrecio }));
      saveSubject.complete();

      // THEN
      expect(detalleListaPrecioFormService.getDetalleListaPrecio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(detalleListaPrecioService.update).toHaveBeenCalledWith(expect.objectContaining(detalleListaPrecio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleListaPrecio>>();
      const detalleListaPrecio = { id: 123 };
      jest.spyOn(detalleListaPrecioFormService, 'getDetalleListaPrecio').mockReturnValue({ id: null });
      jest.spyOn(detalleListaPrecioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleListaPrecio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalleListaPrecio }));
      saveSubject.complete();

      // THEN
      expect(detalleListaPrecioFormService.getDetalleListaPrecio).toHaveBeenCalled();
      expect(detalleListaPrecioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleListaPrecio>>();
      const detalleListaPrecio = { id: 123 };
      jest.spyOn(detalleListaPrecioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleListaPrecio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detalleListaPrecioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareListaPrecio', () => {
      it('Should forward to listaPrecioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(listaPrecioService, 'compareListaPrecio');
        comp.compareListaPrecio(entity, entity2);
        expect(listaPrecioService.compareListaPrecio).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
