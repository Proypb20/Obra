import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../obra.test-samples';

import { ObraFormService } from './obra-form.service';

describe('Obra Form Service', () => {
  let service: ObraFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObraFormService);
  });

  describe('Service methods', () => {
    describe('createObraFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createObraFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            city: expect.any(Object),
            comments: expect.any(Object),
            provincia: expect.any(Object),
            subcontratistas: expect.any(Object),
          })
        );
      });

      it('passing IObra should create a new form with FormGroup', () => {
        const formGroup = service.createObraFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            city: expect.any(Object),
            comments: expect.any(Object),
            provincia: expect.any(Object),
            subcontratistas: expect.any(Object),
          })
        );
      });
    });

    describe('getObra', () => {
      it('should return NewObra for default Obra initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createObraFormGroup(sampleWithNewData);

        const obra = service.getObra(formGroup) as any;

        expect(obra).toMatchObject(sampleWithNewData);
      });

      it('should return NewObra for empty Obra initial value', () => {
        const formGroup = service.createObraFormGroup();

        const obra = service.getObra(formGroup) as any;

        expect(obra).toMatchObject({});
      });

      it('should return IObra', () => {
        const formGroup = service.createObraFormGroup(sampleWithRequiredData);

        const obra = service.getObra(formGroup) as any;

        expect(obra).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IObra should not enable id FormControl', () => {
        const formGroup = service.createObraFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewObra should disable id FormControl', () => {
        const formGroup = service.createObraFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
