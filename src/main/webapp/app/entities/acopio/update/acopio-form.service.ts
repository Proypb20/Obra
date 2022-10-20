import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAcopio, NewAcopio } from '../acopio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAcopio for edit and NewAcopioFormGroupInput for create.
 */
type AcopioFormGroupInput = IAcopio | PartialWithRequiredKeyOf<NewAcopio>;

type AcopioFormDefaults = Pick<NewAcopio, 'id'>;

type AcopioFormGroupContent = {
  id: FormControl<IAcopio['id'] | NewAcopio['id']>;
  date: FormControl<IAcopio['date']>;
  totalAmount: FormControl<IAcopio['totalAmount']>;
  obra: FormControl<IAcopio['obra']>;
  listaprecio: FormControl<IAcopio['listaprecio']>;
  proveedor: FormControl<IAcopio['proveedor']>;
};

export type AcopioFormGroup = FormGroup<AcopioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AcopioFormService {
  createAcopioFormGroup(acopio: AcopioFormGroupInput = { id: null }): AcopioFormGroup {
    const acopioRawValue = {
      ...this.getFormDefaults(),
      ...acopio,
    };
    return new FormGroup<AcopioFormGroupContent>({
      id: new FormControl(
        { value: acopioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      date: new FormControl(acopioRawValue.date),
      totalAmount: new FormControl(acopioRawValue.totalAmount),
      obra: new FormControl(acopioRawValue.obra),
      listaprecio: new FormControl(acopioRawValue.listaprecio),
      proveedor: new FormControl(acopioRawValue.proveedor),
    });
  }

  getAcopio(form: AcopioFormGroup): IAcopio | NewAcopio {
    return form.getRawValue() as IAcopio | NewAcopio;
  }

  resetForm(form: AcopioFormGroup, acopio: AcopioFormGroupInput): void {
    const acopioRawValue = { ...this.getFormDefaults(), ...acopio };
    form.reset(
      {
        ...acopioRawValue,
        id: { value: acopioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AcopioFormDefaults {
    return {
      id: null,
    };
  }
}
