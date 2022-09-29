import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IConcepto, NewConcepto } from '../concepto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IConcepto for edit and NewConceptoFormGroupInput for create.
 */
type ConceptoFormGroupInput = IConcepto | PartialWithRequiredKeyOf<NewConcepto>;

type ConceptoFormDefaults = Pick<NewConcepto, 'id'>;

type ConceptoFormGroupContent = {
  id: FormControl<IConcepto['id'] | NewConcepto['id']>;
  name: FormControl<IConcepto['name']>;
};

export type ConceptoFormGroup = FormGroup<ConceptoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ConceptoFormService {
  createConceptoFormGroup(concepto: ConceptoFormGroupInput = { id: null }): ConceptoFormGroup {
    const conceptoRawValue = {
      ...this.getFormDefaults(),
      ...concepto,
    };
    return new FormGroup<ConceptoFormGroupContent>({
      id: new FormControl(
        { value: conceptoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(conceptoRawValue.name, {
        validators: [Validators.required],
      }),
    });
  }

  getConcepto(form: ConceptoFormGroup): IConcepto | NewConcepto {
    return form.getRawValue() as IConcepto | NewConcepto;
  }

  resetForm(form: ConceptoFormGroup, concepto: ConceptoFormGroupInput): void {
    const conceptoRawValue = { ...this.getFormDefaults(), ...concepto };
    form.reset(
      {
        ...conceptoRawValue,
        id: { value: conceptoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ConceptoFormDefaults {
    return {
      id: null,
    };
  }
}
