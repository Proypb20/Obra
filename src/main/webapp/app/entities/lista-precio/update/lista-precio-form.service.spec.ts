import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../lista-precio.test-samples';

import { ListaPrecioFormService } from './lista-precio-form.service';

describe('ListaPrecio Form Service', () => {
  let service: ListaPrecioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListaPrecioFormService);
  });

  describe('Service methods', () => {
    describe('createListaPrecioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createListaPrecioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            date: expect.any(Object),
            proveedor: expect.any(Object),
          })
        );
      });

      it('passing IListaPrecio should create a new form with FormGroup', () => {
        const formGroup = service.createListaPrecioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            date: expect.any(Object),
            proveedor: expect.any(Object),
          })
        );
      });
    });

    describe('getListaPrecio', () => {
      it('should return NewListaPrecio for default ListaPrecio initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createListaPrecioFormGroup(sampleWithNewData);

        const listaPrecio = service.getListaPrecio(formGroup) as any;

        expect(listaPrecio).toMatchObject(sampleWithNewData);
      });

      it('should return NewListaPrecio for empty ListaPrecio initial value', () => {
        const formGroup = service.createListaPrecioFormGroup();

        const listaPrecio = service.getListaPrecio(formGroup) as any;

        expect(listaPrecio).toMatchObject({});
      });

      it('should return IListaPrecio', () => {
        const formGroup = service.createListaPrecioFormGroup(sampleWithRequiredData);

        const listaPrecio = service.getListaPrecio(formGroup) as any;

        expect(listaPrecio).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IListaPrecio should not enable id FormControl', () => {
        const formGroup = service.createListaPrecioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewListaPrecio should disable id FormControl', () => {
        const formGroup = service.createListaPrecioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
