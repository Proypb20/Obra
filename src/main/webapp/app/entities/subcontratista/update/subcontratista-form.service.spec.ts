import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../subcontratista.test-samples';

import { SubcontratistaFormService } from './subcontratista-form.service';

describe('Subcontratista Form Service', () => {
  let service: SubcontratistaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubcontratistaFormService);
  });

  describe('Service methods', () => {
    describe('createSubcontratistaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSubcontratistaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastName: expect.any(Object),
            firstName: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            obras: expect.any(Object),
          })
        );
      });

      it('passing ISubcontratista should create a new form with FormGroup', () => {
        const formGroup = service.createSubcontratistaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastName: expect.any(Object),
            firstName: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            obras: expect.any(Object),
          })
        );
      });
    });

    describe('getSubcontratista', () => {
      it('should return NewSubcontratista for default Subcontratista initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSubcontratistaFormGroup(sampleWithNewData);

        const subcontratista = service.getSubcontratista(formGroup) as any;

        expect(subcontratista).toMatchObject(sampleWithNewData);
      });

      it('should return NewSubcontratista for empty Subcontratista initial value', () => {
        const formGroup = service.createSubcontratistaFormGroup();

        const subcontratista = service.getSubcontratista(formGroup) as any;

        expect(subcontratista).toMatchObject({});
      });

      it('should return ISubcontratista', () => {
        const formGroup = service.createSubcontratistaFormGroup(sampleWithRequiredData);

        const subcontratista = service.getSubcontratista(formGroup) as any;

        expect(subcontratista).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISubcontratista should not enable id FormControl', () => {
        const formGroup = service.createSubcontratistaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSubcontratista should disable id FormControl', () => {
        const formGroup = service.createSubcontratistaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
