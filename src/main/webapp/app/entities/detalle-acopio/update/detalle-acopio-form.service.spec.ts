import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../detalle-acopio.test-samples';

import { DetalleAcopioFormService } from './detalle-acopio-form.service';

describe('DetalleAcopio Form Service', () => {
  let service: DetalleAcopioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DetalleAcopioFormService);
  });

  describe('Service methods', () => {
    describe('createDetalleAcopioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDetalleAcopioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            description: expect.any(Object),
            quantity: expect.any(Object),
            unitPrice: expect.any(Object),
            amount: expect.any(Object),
            requestDate: expect.any(Object),
            promiseDate: expect.any(Object),
            deliveryStatus: expect.any(Object),
            acopio: expect.any(Object),
            detalleListaPrecio: expect.any(Object),
          })
        );
      });

      it('passing IDetalleAcopio should create a new form with FormGroup', () => {
        const formGroup = service.createDetalleAcopioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            description: expect.any(Object),
            quantity: expect.any(Object),
            unitPrice: expect.any(Object),
            amount: expect.any(Object),
            requestDate: expect.any(Object),
            promiseDate: expect.any(Object),
            deliveryStatus: expect.any(Object),
            acopio: expect.any(Object),
            detalleListaPrecio: expect.any(Object),
          })
        );
      });
    });

    describe('getDetalleAcopio', () => {
      it('should return NewDetalleAcopio for default DetalleAcopio initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDetalleAcopioFormGroup(sampleWithNewData);

        const detalleAcopio = service.getDetalleAcopio(formGroup) as any;

        expect(detalleAcopio).toMatchObject(sampleWithNewData);
      });

      it('should return NewDetalleAcopio for empty DetalleAcopio initial value', () => {
        const formGroup = service.createDetalleAcopioFormGroup();

        const detalleAcopio = service.getDetalleAcopio(formGroup) as any;

        expect(detalleAcopio).toMatchObject({});
      });

      it('should return IDetalleAcopio', () => {
        const formGroup = service.createDetalleAcopioFormGroup(sampleWithRequiredData);

        const detalleAcopio = service.getDetalleAcopio(formGroup) as any;

        expect(detalleAcopio).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDetalleAcopio should not enable id FormControl', () => {
        const formGroup = service.createDetalleAcopioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDetalleAcopio should disable id FormControl', () => {
        const formGroup = service.createDetalleAcopioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
