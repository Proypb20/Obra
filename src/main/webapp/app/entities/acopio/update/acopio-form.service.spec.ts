import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../acopio.test-samples';

import { AcopioFormService } from './acopio-form.service';

describe('Acopio Form Service', () => {
  let service: AcopioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AcopioFormService);
  });

  describe('Service methods', () => {
    describe('createAcopioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAcopioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            totalAmount: expect.any(Object),
            obra: expect.any(Object),
            listaprecio: expect.any(Object),
            proveedor: expect.any(Object),
          })
        );
      });

      it('passing IAcopio should create a new form with FormGroup', () => {
        const formGroup = service.createAcopioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            totalAmount: expect.any(Object),
            obra: expect.any(Object),
            listaprecio: expect.any(Object),
            proveedor: expect.any(Object),
          })
        );
      });
    });

    describe('getAcopio', () => {
      it('should return NewAcopio for default Acopio initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAcopioFormGroup(sampleWithNewData);

        const acopio = service.getAcopio(formGroup) as any;

        expect(acopio).toMatchObject(sampleWithNewData);
      });

      it('should return NewAcopio for empty Acopio initial value', () => {
        const formGroup = service.createAcopioFormGroup();

        const acopio = service.getAcopio(formGroup) as any;

        expect(acopio).toMatchObject({});
      });

      it('should return IAcopio', () => {
        const formGroup = service.createAcopioFormGroup(sampleWithRequiredData);

        const acopio = service.getAcopio(formGroup) as any;

        expect(acopio).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAcopio should not enable id FormControl', () => {
        const formGroup = service.createAcopioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAcopio should disable id FormControl', () => {
        const formGroup = service.createAcopioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
