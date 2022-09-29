import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProvincia, NewProvincia } from '../provincia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProvincia for edit and NewProvinciaFormGroupInput for create.
 */
type ProvinciaFormGroupInput = IProvincia | PartialWithRequiredKeyOf<NewProvincia>;

type ProvinciaFormDefaults = Pick<NewProvincia, 'id'>;

type ProvinciaFormGroupContent = {
  id: FormControl<IProvincia['id'] | NewProvincia['id']>;
  name: FormControl<IProvincia['name']>;
};

export type ProvinciaFormGroup = FormGroup<ProvinciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProvinciaFormService {
  createProvinciaFormGroup(provincia: ProvinciaFormGroupInput = { id: null }): ProvinciaFormGroup {
    const provinciaRawValue = {
      ...this.getFormDefaults(),
      ...provincia,
    };
    return new FormGroup<ProvinciaFormGroupContent>({
      id: new FormControl(
        { value: provinciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(provinciaRawValue.name, {
        validators: [Validators.required],
      }),
    });
  }

  getProvincia(form: ProvinciaFormGroup): IProvincia | NewProvincia {
    return form.getRawValue() as IProvincia | NewProvincia;
  }

  resetForm(form: ProvinciaFormGroup, provincia: ProvinciaFormGroupInput): void {
    const provinciaRawValue = { ...this.getFormDefaults(), ...provincia };
    form.reset(
      {
        ...provinciaRawValue,
        id: { value: provinciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProvinciaFormDefaults {
    return {
      id: null,
    };
  }
}
