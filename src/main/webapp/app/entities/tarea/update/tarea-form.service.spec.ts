import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarea.test-samples';

import { TareaFormService } from './tarea-form.service';

describe('Tarea Form Service', () => {
  let service: TareaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TareaFormService);
  });

  describe('Service methods', () => {
    describe('createTareaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTareaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            quantity: expect.any(Object),
            cost: expect.any(Object),
            advanceStatus: expect.any(Object),
            obra: expect.any(Object),
            subcontratista: expect.any(Object),
            concepto: expect.any(Object),
          })
        );
      });

      it('passing ITarea should create a new form with FormGroup', () => {
        const formGroup = service.createTareaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            quantity: expect.any(Object),
            cost: expect.any(Object),
            advanceStatus: expect.any(Object),
            obra: expect.any(Object),
            subcontratista: expect.any(Object),
            concepto: expect.any(Object),
          })
        );
      });
    });

    describe('getTarea', () => {
      it('should return NewTarea for default Tarea initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTareaFormGroup(sampleWithNewData);

        const tarea = service.getTarea(formGroup) as any;

        expect(tarea).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarea for empty Tarea initial value', () => {
        const formGroup = service.createTareaFormGroup();

        const tarea = service.getTarea(formGroup) as any;

        expect(tarea).toMatchObject({});
      });

      it('should return ITarea', () => {
        const formGroup = service.createTareaFormGroup(sampleWithRequiredData);

        const tarea = service.getTarea(formGroup) as any;

        expect(tarea).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarea should not enable id FormControl', () => {
        const formGroup = service.createTareaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarea should disable id FormControl', () => {
        const formGroup = service.createTareaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
