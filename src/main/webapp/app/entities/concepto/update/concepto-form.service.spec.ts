import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../concepto.test-samples';

import { ConceptoFormService } from './concepto-form.service';

describe('Concepto Form Service', () => {
  let service: ConceptoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConceptoFormService);
  });

  describe('Service methods', () => {
    describe('createConceptoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createConceptoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });

      it('passing IConcepto should create a new form with FormGroup', () => {
        const formGroup = service.createConceptoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });
    });

    describe('getConcepto', () => {
      it('should return NewConcepto for default Concepto initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createConceptoFormGroup(sampleWithNewData);

        const concepto = service.getConcepto(formGroup) as any;

        expect(concepto).toMatchObject(sampleWithNewData);
      });

      it('should return NewConcepto for empty Concepto initial value', () => {
        const formGroup = service.createConceptoFormGroup();

        const concepto = service.getConcepto(formGroup) as any;

        expect(concepto).toMatchObject({});
      });

      it('should return IConcepto', () => {
        const formGroup = service.createConceptoFormGroup(sampleWithRequiredData);

        const concepto = service.getConcepto(formGroup) as any;

        expect(concepto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IConcepto should not enable id FormControl', () => {
        const formGroup = service.createConceptoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewConcepto should disable id FormControl', () => {
        const formGroup = service.createConceptoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
