import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICliente, NewCliente } from '../cliente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICliente for edit and NewClienteFormGroupInput for create.
 */
type ClienteFormGroupInput = ICliente | PartialWithRequiredKeyOf<NewCliente>;

type ClienteFormDefaults = Pick<NewCliente, 'id' | 'obras'>;

type ClienteFormGroupContent = {
  id: FormControl<ICliente['id'] | NewCliente['id']>;
  lastName: FormControl<ICliente['lastName']>;
  firstName: FormControl<ICliente['firstName']>;
  taxpayerID: FormControl<ICliente['taxpayerID']>;
  address: FormControl<ICliente['address']>;
  city: FormControl<ICliente['city']>;
  provincia: FormControl<ICliente['provincia']>;
  obras: FormControl<ICliente['obras']>;
};

export type ClienteFormGroup = FormGroup<ClienteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClienteFormService {
  createClienteFormGroup(cliente: ClienteFormGroupInput = { id: null }): ClienteFormGroup {
    const clienteRawValue = {
      ...this.getFormDefaults(),
      ...cliente,
    };
    return new FormGroup<ClienteFormGroupContent>({
      id: new FormControl(
        { value: clienteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      lastName: new FormControl(clienteRawValue.lastName, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(clienteRawValue.firstName, {
        validators: [Validators.required],
      }),
      taxpayerID: new FormControl(clienteRawValue.taxpayerID),
      address: new FormControl(clienteRawValue.address),
      city: new FormControl(clienteRawValue.city),
      provincia: new FormControl(clienteRawValue.provincia),
      obras: new FormControl(clienteRawValue.obras ?? []),
    });
  }

  getCliente(form: ClienteFormGroup): ICliente | NewCliente {
    return form.getRawValue() as ICliente | NewCliente;
  }

  resetForm(form: ClienteFormGroup, cliente: ClienteFormGroupInput): void {
    const clienteRawValue = { ...this.getFormDefaults(), ...cliente };
    form.reset(
      {
        ...clienteRawValue,
        id: { value: clienteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ClienteFormDefaults {
    return {
      id: null,
      obras: [],
    };
  }
}
