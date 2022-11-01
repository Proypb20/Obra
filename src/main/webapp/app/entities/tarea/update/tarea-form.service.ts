import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITarea, NewTarea } from '../tarea.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarea for edit and NewTareaFormGroupInput for create.
 */
type TareaFormGroupInput = ITarea | PartialWithRequiredKeyOf<NewTarea>;

type TareaFormDefaults = Pick<NewTarea, 'id'>;

type TareaFormGroupContent = {
  id: FormControl<ITarea['id'] | NewTarea['id']>;
  name: FormControl<ITarea['name']>;
  quantity: FormControl<ITarea['quantity']>;
  cost: FormControl<ITarea['cost']>;
  advanceStatus: FormControl<ITarea['advanceStatus']>;
  obra: FormControl<ITarea['obra']>;
  subcontratista: FormControl<ITarea['subcontratista']>;
  concepto: FormControl<ITarea['concepto']>;
};

export type TareaFormGroup = FormGroup<TareaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TareaFormService {
  createTareaFormGroup(tarea: TareaFormGroupInput = { id: null }): TareaFormGroup {
    const tareaRawValue = {
      ...this.getFormDefaults(),
      ...tarea,
    };
    return new FormGroup<TareaFormGroupContent>({
      id: new FormControl(
        { value: tareaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(tareaRawValue.name, {
        validators: [Validators.required],
      }),
      quantity: new FormControl(tareaRawValue.quantity, {
        validators: [Validators.required],
      }),
      cost: new FormControl(tareaRawValue.cost, {
        validators: [Validators.required],
      }),
      advanceStatus: new FormControl(tareaRawValue.advanceStatus, {
        validators: [Validators.max(100), Validators.required],
      }),
      obra: new FormControl(tareaRawValue.obra),
      subcontratista: new FormControl(tareaRawValue.subcontratista),
      concepto: new FormControl(tareaRawValue.concepto, {
        validators: [Validators.required],
      }),
    });
  }

  getTarea(form: TareaFormGroup): ITarea | NewTarea {
    return form.getRawValue() as ITarea | NewTarea;
  }

  resetForm(form: TareaFormGroup, tarea: TareaFormGroupInput): void {
    const tareaRawValue = { ...this.getFormDefaults(), ...tarea };
    form.reset(
      {
        ...tareaRawValue,
        id: { value: tareaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TareaFormDefaults {
    return {
      id: null,
    };
  }
}
