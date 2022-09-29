import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISubcontratista, NewSubcontratista } from '../subcontratista.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISubcontratista for edit and NewSubcontratistaFormGroupInput for create.
 */
type SubcontratistaFormGroupInput = ISubcontratista | PartialWithRequiredKeyOf<NewSubcontratista>;

type SubcontratistaFormDefaults = Pick<NewSubcontratista, 'id' | 'obras'>;

type SubcontratistaFormGroupContent = {
  id: FormControl<ISubcontratista['id'] | NewSubcontratista['id']>;
  lastName: FormControl<ISubcontratista['lastName']>;
  firstName: FormControl<ISubcontratista['firstName']>;
  phone: FormControl<ISubcontratista['phone']>;
  email: FormControl<ISubcontratista['email']>;
  obras: FormControl<ISubcontratista['obras']>;
};

export type SubcontratistaFormGroup = FormGroup<SubcontratistaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SubcontratistaFormService {
  createSubcontratistaFormGroup(subcontratista: SubcontratistaFormGroupInput = { id: null }): SubcontratistaFormGroup {
    const subcontratistaRawValue = {
      ...this.getFormDefaults(),
      ...subcontratista,
    };
    return new FormGroup<SubcontratistaFormGroupContent>({
      id: new FormControl(
        { value: subcontratistaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      lastName: new FormControl(subcontratistaRawValue.lastName, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(subcontratistaRawValue.firstName, {
        validators: [Validators.required],
      }),
      phone: new FormControl(subcontratistaRawValue.phone),
      email: new FormControl(subcontratistaRawValue.email),
      obras: new FormControl(subcontratistaRawValue.obras ?? []),
    });
  }

  getSubcontratista(form: SubcontratistaFormGroup): ISubcontratista | NewSubcontratista {
    return form.getRawValue() as ISubcontratista | NewSubcontratista;
  }

  resetForm(form: SubcontratistaFormGroup, subcontratista: SubcontratistaFormGroupInput): void {
    const subcontratistaRawValue = { ...this.getFormDefaults(), ...subcontratista };
    form.reset(
      {
        ...subcontratistaRawValue,
        id: { value: subcontratistaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SubcontratistaFormDefaults {
    return {
      id: null,
      obras: [],
    };
  }
}
