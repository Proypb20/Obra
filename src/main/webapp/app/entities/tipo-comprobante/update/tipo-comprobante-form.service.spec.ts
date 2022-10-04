import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tipo-comprobante.test-samples';

import { TipoComprobanteFormService } from './tipo-comprobante-form.service';

describe('TipoComprobante Form Service', () => {
  let service: TipoComprobanteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TipoComprobanteFormService);
  });

  describe('Service methods', () => {
    describe('createTipoComprobanteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTipoComprobanteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });

      it('passing ITipoComprobante should create a new form with FormGroup', () => {
        const formGroup = service.createTipoComprobanteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });
    });

    describe('getTipoComprobante', () => {
      it('should return NewTipoComprobante for default TipoComprobante initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTipoComprobanteFormGroup(sampleWithNewData);

        const tipoComprobante = service.getTipoComprobante(formGroup) as any;

        expect(tipoComprobante).toMatchObject(sampleWithNewData);
      });

      it('should return NewTipoComprobante for empty TipoComprobante initial value', () => {
        const formGroup = service.createTipoComprobanteFormGroup();

        const tipoComprobante = service.getTipoComprobante(formGroup) as any;

        expect(tipoComprobante).toMatchObject({});
      });

      it('should return ITipoComprobante', () => {
        const formGroup = service.createTipoComprobanteFormGroup(sampleWithRequiredData);

        const tipoComprobante = service.getTipoComprobante(formGroup) as any;

        expect(tipoComprobante).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITipoComprobante should not enable id FormControl', () => {
        const formGroup = service.createTipoComprobanteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTipoComprobante should disable id FormControl', () => {
        const formGroup = service.createTipoComprobanteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
