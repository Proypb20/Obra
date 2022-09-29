import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../transaccion.test-samples';

import { TransaccionFormService } from './transaccion-form.service';

describe('Transaccion Form Service', () => {
  let service: TransaccionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransaccionFormService);
  });

  describe('Service methods', () => {
    describe('createTransaccionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTransaccionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            paymentMethod: expect.any(Object),
            transactionNumber: expect.any(Object),
            amount: expect.any(Object),
            note: expect.any(Object),
            obra: expect.any(Object),
            subcontratista: expect.any(Object),
            tipoComprobante: expect.any(Object),
            concepto: expect.any(Object),
          })
        );
      });

      it('passing ITransaccion should create a new form with FormGroup', () => {
        const formGroup = service.createTransaccionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            paymentMethod: expect.any(Object),
            transactionNumber: expect.any(Object),
            amount: expect.any(Object),
            note: expect.any(Object),
            obra: expect.any(Object),
            subcontratista: expect.any(Object),
            tipoComprobante: expect.any(Object),
            concepto: expect.any(Object),
          })
        );
      });
    });

    describe('getTransaccion', () => {
      it('should return NewTransaccion for default Transaccion initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTransaccionFormGroup(sampleWithNewData);

        const transaccion = service.getTransaccion(formGroup) as any;

        expect(transaccion).toMatchObject(sampleWithNewData);
      });

      it('should return NewTransaccion for empty Transaccion initial value', () => {
        const formGroup = service.createTransaccionFormGroup();

        const transaccion = service.getTransaccion(formGroup) as any;

        expect(transaccion).toMatchObject({});
      });

      it('should return ITransaccion', () => {
        const formGroup = service.createTransaccionFormGroup(sampleWithRequiredData);

        const transaccion = service.getTransaccion(formGroup) as any;

        expect(transaccion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITransaccion should not enable id FormControl', () => {
        const formGroup = service.createTransaccionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTransaccion should disable id FormControl', () => {
        const formGroup = service.createTransaccionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
