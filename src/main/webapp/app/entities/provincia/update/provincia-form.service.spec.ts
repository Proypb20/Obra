import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../provincia.test-samples';

import { ProvinciaFormService } from './provincia-form.service';

describe('Provincia Form Service', () => {
  let service: ProvinciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProvinciaFormService);
  });

  describe('Service methods', () => {
    describe('createProvinciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProvinciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });

      it('passing IProvincia should create a new form with FormGroup', () => {
        const formGroup = service.createProvinciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });
    });

    describe('getProvincia', () => {
      it('should return NewProvincia for default Provincia initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProvinciaFormGroup(sampleWithNewData);

        const provincia = service.getProvincia(formGroup) as any;

        expect(provincia).toMatchObject(sampleWithNewData);
      });

      it('should return NewProvincia for empty Provincia initial value', () => {
        const formGroup = service.createProvinciaFormGroup();

        const provincia = service.getProvincia(formGroup) as any;

        expect(provincia).toMatchObject({});
      });

      it('should return IProvincia', () => {
        const formGroup = service.createProvinciaFormGroup(sampleWithRequiredData);

        const provincia = service.getProvincia(formGroup) as any;

        expect(provincia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProvincia should not enable id FormControl', () => {
        const formGroup = service.createProvinciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProvincia should disable id FormControl', () => {
        const formGroup = service.createProvinciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
