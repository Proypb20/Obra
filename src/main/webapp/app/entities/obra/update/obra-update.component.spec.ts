import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ObraFormService } from './obra-form.service';
import { ObraService } from '../service/obra.service';
import { IObra } from '../obra.model';
import { IProvincia } from 'app/entities/provincia/provincia.model';
import { ProvinciaService } from 'app/entities/provincia/service/provincia.service';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { SubcontratistaService } from 'app/entities/subcontratista/service/subcontratista.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

import { ObraUpdateComponent } from './obra-update.component';

describe('Obra Management Update Component', () => {
  let comp: ObraUpdateComponent;
  let fixture: ComponentFixture<ObraUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let obraFormService: ObraFormService;
  let obraService: ObraService;
  let provinciaService: ProvinciaService;
  let subcontratistaService: SubcontratistaService;
  let clienteService: ClienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ObraUpdateComponent],
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
      .overrideTemplate(ObraUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ObraUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    obraFormService = TestBed.inject(ObraFormService);
    obraService = TestBed.inject(ObraService);
    provinciaService = TestBed.inject(ProvinciaService);
    subcontratistaService = TestBed.inject(SubcontratistaService);
    clienteService = TestBed.inject(ClienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Provincia query and add missing value', () => {
      const obra: IObra = { id: 456 };
      const provincia: IProvincia = { id: 95171 };
      obra.provincia = provincia;

      const provinciaCollection: IProvincia[] = [{ id: 14389 }];
      jest.spyOn(provinciaService, 'query').mockReturnValue(of(new HttpResponse({ body: provinciaCollection })));
      const additionalProvincias = [provincia];
      const expectedCollection: IProvincia[] = [...additionalProvincias, ...provinciaCollection];
      jest.spyOn(provinciaService, 'addProvinciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ obra });
      comp.ngOnInit();

      expect(provinciaService.query).toHaveBeenCalled();
      expect(provinciaService.addProvinciaToCollectionIfMissing).toHaveBeenCalledWith(
        provinciaCollection,
        ...additionalProvincias.map(expect.objectContaining)
      );
      expect(comp.provinciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Subcontratista query and add missing value', () => {
      const obra: IObra = { id: 456 };
      const subcontratistas: ISubcontratista[] = [{ id: 25840 }];
      obra.subcontratistas = subcontratistas;

      const subcontratistaCollection: ISubcontratista[] = [{ id: 32033 }];
      jest.spyOn(subcontratistaService, 'query').mockReturnValue(of(new HttpResponse({ body: subcontratistaCollection })));
      const additionalSubcontratistas = [...subcontratistas];
      const expectedCollection: ISubcontratista[] = [...additionalSubcontratistas, ...subcontratistaCollection];
      jest.spyOn(subcontratistaService, 'addSubcontratistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ obra });
      comp.ngOnInit();

      expect(subcontratistaService.query).toHaveBeenCalled();
      expect(subcontratistaService.addSubcontratistaToCollectionIfMissing).toHaveBeenCalledWith(
        subcontratistaCollection,
        ...additionalSubcontratistas.map(expect.objectContaining)
      );
      expect(comp.subcontratistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Cliente query and add missing value', () => {
      const obra: IObra = { id: 456 };
      const clientes: ICliente[] = [{ id: 45604 }];
      obra.clientes = clientes;

      const clienteCollection: ICliente[] = [{ id: 62806 }];
      jest.spyOn(clienteService, 'query').mockReturnValue(of(new HttpResponse({ body: clienteCollection })));
      const additionalClientes = [...clientes];
      const expectedCollection: ICliente[] = [...additionalClientes, ...clienteCollection];
      jest.spyOn(clienteService, 'addClienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ obra });
      comp.ngOnInit();

      expect(clienteService.query).toHaveBeenCalled();
      expect(clienteService.addClienteToCollectionIfMissing).toHaveBeenCalledWith(
        clienteCollection,
        ...additionalClientes.map(expect.objectContaining)
      );
      expect(comp.clientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const obra: IObra = { id: 456 };
      const provincia: IProvincia = { id: 20013 };
      obra.provincia = provincia;
      const subcontratista: ISubcontratista = { id: 22542 };
      obra.subcontratistas = [subcontratista];
      const cliente: ICliente = { id: 79475 };
      obra.clientes = [cliente];

      activatedRoute.data = of({ obra });
      comp.ngOnInit();

      expect(comp.provinciasSharedCollection).toContain(provincia);
      expect(comp.subcontratistasSharedCollection).toContain(subcontratista);
      expect(comp.clientesSharedCollection).toContain(cliente);
      expect(comp.obra).toEqual(obra);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObra>>();
      const obra = { id: 123 };
      jest.spyOn(obraFormService, 'getObra').mockReturnValue(obra);
      jest.spyOn(obraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ obra });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: obra }));
      saveSubject.complete();

      // THEN
      expect(obraFormService.getObra).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(obraService.update).toHaveBeenCalledWith(expect.objectContaining(obra));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObra>>();
      const obra = { id: 123 };
      jest.spyOn(obraFormService, 'getObra').mockReturnValue({ id: null });
      jest.spyOn(obraService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ obra: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: obra }));
      saveSubject.complete();

      // THEN
      expect(obraFormService.getObra).toHaveBeenCalled();
      expect(obraService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObra>>();
      const obra = { id: 123 };
      jest.spyOn(obraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ obra });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(obraService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProvincia', () => {
      it('Should forward to provinciaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(provinciaService, 'compareProvincia');
        comp.compareProvincia(entity, entity2);
        expect(provinciaService.compareProvincia).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareCliente', () => {
      it('Should forward to clienteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clienteService, 'compareCliente');
        comp.compareCliente(entity, entity2);
        expect(clienteService.compareCliente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
