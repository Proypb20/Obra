import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TipoComprobanteFormService } from './tipo-comprobante-form.service';
import { TipoComprobanteService } from '../service/tipo-comprobante.service';
import { ITipoComprobante } from '../tipo-comprobante.model';

import { TipoComprobanteUpdateComponent } from './tipo-comprobante-update.component';

describe('TipoComprobante Management Update Component', () => {
  let comp: TipoComprobanteUpdateComponent;
  let fixture: ComponentFixture<TipoComprobanteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoComprobanteFormService: TipoComprobanteFormService;
  let tipoComprobanteService: TipoComprobanteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TipoComprobanteUpdateComponent],
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
      .overrideTemplate(TipoComprobanteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoComprobanteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoComprobanteFormService = TestBed.inject(TipoComprobanteFormService);
    tipoComprobanteService = TestBed.inject(TipoComprobanteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tipoComprobante: ITipoComprobante = { id: 456 };

      activatedRoute.data = of({ tipoComprobante });
      comp.ngOnInit();

      expect(comp.tipoComprobante).toEqual(tipoComprobante);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoComprobante>>();
      const tipoComprobante = { id: 123 };
      jest.spyOn(tipoComprobanteFormService, 'getTipoComprobante').mockReturnValue(tipoComprobante);
      jest.spyOn(tipoComprobanteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoComprobante });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoComprobante }));
      saveSubject.complete();

      // THEN
      expect(tipoComprobanteFormService.getTipoComprobante).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoComprobanteService.update).toHaveBeenCalledWith(expect.objectContaining(tipoComprobante));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoComprobante>>();
      const tipoComprobante = { id: 123 };
      jest.spyOn(tipoComprobanteFormService, 'getTipoComprobante').mockReturnValue({ id: null });
      jest.spyOn(tipoComprobanteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoComprobante: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoComprobante }));
      saveSubject.complete();

      // THEN
      expect(tipoComprobanteFormService.getTipoComprobante).toHaveBeenCalled();
      expect(tipoComprobanteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoComprobante>>();
      const tipoComprobante = { id: 123 };
      jest.spyOn(tipoComprobanteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoComprobante });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoComprobanteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
