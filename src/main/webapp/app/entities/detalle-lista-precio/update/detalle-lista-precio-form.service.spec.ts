import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../detalle-lista-precio.test-samples';

import { DetalleListaPrecioFormService } from './detalle-lista-precio-form.service';

describe('DetalleListaPrecio Form Service', () => {
  let service: DetalleListaPrecioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DetalleListaPrecioFormService);
  });

  describe('Service methods', () => {
    describe('createDetalleListaPrecioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDetalleListaPrecioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            product: expect.any(Object),
            amount: expect.any(Object),
            listaPrecio: expect.any(Object),
          })
        );
      });

      it('passing IDetalleListaPrecio should create a new form with FormGroup', () => {
        const formGroup = service.createDetalleListaPrecioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            product: expect.any(Object),
            amount: expect.any(Object),
            listaPrecio: expect.any(Object),
          })
        );
      });
    });

    describe('getDetalleListaPrecio', () => {
      it('should return NewDetalleListaPrecio for default DetalleListaPrecio initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDetalleListaPrecioFormGroup(sampleWithNewData);

        const detalleListaPrecio = service.getDetalleListaPrecio(formGroup) as any;

        expect(detalleListaPrecio).toMatchObject(sampleWithNewData);
      });

      it('should return NewDetalleListaPrecio for empty DetalleListaPrecio initial value', () => {
        const formGroup = service.createDetalleListaPrecioFormGroup();

        const detalleListaPrecio = service.getDetalleListaPrecio(formGroup) as any;

        expect(detalleListaPrecio).toMatchObject({});
      });

      it('should return IDetalleListaPrecio', () => {
        const formGroup = service.createDetalleListaPrecioFormGroup(sampleWithRequiredData);

        const detalleListaPrecio = service.getDetalleListaPrecio(formGroup) as any;

        expect(detalleListaPrecio).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDetalleListaPrecio should not enable id FormControl', () => {
        const formGroup = service.createDetalleListaPrecioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDetalleListaPrecio should disable id FormControl', () => {
        const formGroup = service.createDetalleListaPrecioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
