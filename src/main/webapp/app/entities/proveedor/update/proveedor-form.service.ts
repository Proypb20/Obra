import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProveedor, NewProveedor } from '../proveedor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProveedor for edit and NewProveedorFormGroupInput for create.
 */
type ProveedorFormGroupInput = IProveedor | PartialWithRequiredKeyOf<NewProveedor>;

type ProveedorFormDefaults = Pick<NewProveedor, 'id'>;

type ProveedorFormGroupContent = {
  id: FormControl<IProveedor['id'] | NewProveedor['id']>;
  name: FormControl<IProveedor['name']>;
  address: FormControl<IProveedor['address']>;
  city: FormControl<IProveedor['city']>;
  phone: FormControl<IProveedor['phone']>;
  email: FormControl<IProveedor['email']>;
  provincia: FormControl<IProveedor['provincia']>;
};

export type ProveedorFormGroup = FormGroup<ProveedorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProveedorFormService {
  createProveedorFormGroup(proveedor: ProveedorFormGroupInput = { id: null }): ProveedorFormGroup {
    const proveedorRawValue = {
      ...this.getFormDefaults(),
      ...proveedor,
    };
    return new FormGroup<ProveedorFormGroupContent>({
      id: new FormControl(
        { value: proveedorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(proveedorRawValue.name, {
        validators: [Validators.required],
      }),
      address: new FormControl(proveedorRawValue.address),
      city: new FormControl(proveedorRawValue.city),
      phone: new FormControl(proveedorRawValue.phone),
      email: new FormControl(proveedorRawValue.email),
      provincia: new FormControl(proveedorRawValue.provincia),
    });
  }

  getProveedor(form: ProveedorFormGroup): IProveedor | NewProveedor {
    return form.getRawValue() as IProveedor | NewProveedor;
  }

  resetForm(form: ProveedorFormGroup, proveedor: ProveedorFormGroupInput): void {
    const proveedorRawValue = { ...this.getFormDefaults(), ...proveedor };
    form.reset(
      {
        ...proveedorRawValue,
        id: { value: proveedorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProveedorFormDefaults {
    return {
      id: null,
    };
  }
}
