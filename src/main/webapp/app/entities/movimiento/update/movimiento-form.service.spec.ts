import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../movimiento.test-samples';

import { MovimientoFormService } from './movimiento-form.service';

describe('Movimiento Form Service', () => {
  let service: MovimientoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovimientoFormService);
  });

  describe('Service methods', () => {
    describe('createMovimientoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMovimientoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            description: expect.any(Object),
            metodoPago: expect.any(Object),
            amount: expect.any(Object),
            transactionNumber: expect.any(Object),
            obra: expect.any(Object),
            subcontratista: expect.any(Object),
            concepto: expect.any(Object),
            tipoComprobante: expect.any(Object),
          })
        );
      });

      it('passing IMovimiento should create a new form with FormGroup', () => {
        const formGroup = service.createMovimientoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            description: expect.any(Object),
            metodoPago: expect.any(Object),
            amount: expect.any(Object),
            transactionNumber: expect.any(Object),
            obra: expect.any(Object),
            subcontratista: expect.any(Object),
            concepto: expect.any(Object),
            tipoComprobante: expect.any(Object),
          })
        );
      });
    });

    describe('getMovimiento', () => {
      it('should return NewMovimiento for default Movimiento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMovimientoFormGroup(sampleWithNewData);

        const movimiento = service.getMovimiento(formGroup) as any;

        expect(movimiento).toMatchObject(sampleWithNewData);
      });

      it('should return NewMovimiento for empty Movimiento initial value', () => {
        const formGroup = service.createMovimientoFormGroup();

        const movimiento = service.getMovimiento(formGroup) as any;

        expect(movimiento).toMatchObject({});
      });

      it('should return IMovimiento', () => {
        const formGroup = service.createMovimientoFormGroup(sampleWithRequiredData);

        const movimiento = service.getMovimiento(formGroup) as any;

        expect(movimiento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMovimiento should not enable id FormControl', () => {
        const formGroup = service.createMovimientoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMovimiento should disable id FormControl', () => {
        const formGroup = service.createMovimientoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
