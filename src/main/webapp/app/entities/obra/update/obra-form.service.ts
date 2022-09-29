import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IObra, NewObra } from '../obra.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IObra for edit and NewObraFormGroupInput for create.
 */
type ObraFormGroupInput = IObra | PartialWithRequiredKeyOf<NewObra>;

type ObraFormDefaults = Pick<NewObra, 'id' | 'subcontratistas'>;

type ObraFormGroupContent = {
  id: FormControl<IObra['id'] | NewObra['id']>;
  name: FormControl<IObra['name']>;
  address: FormControl<IObra['address']>;
  city: FormControl<IObra['city']>;
  comments: FormControl<IObra['comments']>;
  provincia: FormControl<IObra['provincia']>;
  subcontratistas: FormControl<IObra['subcontratistas']>;
};

export type ObraFormGroup = FormGroup<ObraFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ObraFormService {
  createObraFormGroup(obra: ObraFormGroupInput = { id: null }): ObraFormGroup {
    const obraRawValue = {
      ...this.getFormDefaults(),
      ...obra,
    };
    return new FormGroup<ObraFormGroupContent>({
      id: new FormControl(
        { value: obraRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(obraRawValue.name, {
        validators: [Validators.required],
      }),
      address: new FormControl(obraRawValue.address),
      city: new FormControl(obraRawValue.city),
      comments: new FormControl(obraRawValue.comments, {
        validators: [Validators.required],
      }),
      provincia: new FormControl(obraRawValue.provincia),
      subcontratistas: new FormControl(obraRawValue.subcontratistas ?? []),
    });
  }

  getObra(form: ObraFormGroup): IObra | NewObra {
    return form.getRawValue() as IObra | NewObra;
  }

  resetForm(form: ObraFormGroup, obra: ObraFormGroupInput): void {
    const obraRawValue = { ...this.getFormDefaults(), ...obra };
    form.reset(
      {
        ...obraRawValue,
        id: { value: obraRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ObraFormDefaults {
    return {
      id: null,
      subcontratistas: [],
    };
  }
}
