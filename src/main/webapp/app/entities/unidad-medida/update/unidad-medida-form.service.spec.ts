import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../unidad-medida.test-samples';

import { UnidadMedidaFormService } from './unidad-medida-form.service';

describe('UnidadMedida Form Service', () => {
  let service: UnidadMedidaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UnidadMedidaFormService);
  });

  describe('Service methods', () => {
    describe('createUnidadMedidaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUnidadMedidaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });

      it('passing IUnidadMedida should create a new form with FormGroup', () => {
        const formGroup = service.createUnidadMedidaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });
    });

    describe('getUnidadMedida', () => {
      it('should return NewUnidadMedida for default UnidadMedida initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUnidadMedidaFormGroup(sampleWithNewData);

        const unidadMedida = service.getUnidadMedida(formGroup) as any;

        expect(unidadMedida).toMatchObject(sampleWithNewData);
      });

      it('should return NewUnidadMedida for empty UnidadMedida initial value', () => {
        const formGroup = service.createUnidadMedidaFormGroup();

        const unidadMedida = service.getUnidadMedida(formGroup) as any;

        expect(unidadMedida).toMatchObject({});
      });

      it('should return IUnidadMedida', () => {
        const formGroup = service.createUnidadMedidaFormGroup(sampleWithRequiredData);

        const unidadMedida = service.getUnidadMedida(formGroup) as any;

        expect(unidadMedida).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUnidadMedida should not enable id FormControl', () => {
        const formGroup = service.createUnidadMedidaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUnidadMedida should disable id FormControl', () => {
        const formGroup = service.createUnidadMedidaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
