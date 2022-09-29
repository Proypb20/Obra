import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IListaPrecio, NewListaPrecio } from '../lista-precio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IListaPrecio for edit and NewListaPrecioFormGroupInput for create.
 */
type ListaPrecioFormGroupInput = IListaPrecio | PartialWithRequiredKeyOf<NewListaPrecio>;

type ListaPrecioFormDefaults = Pick<NewListaPrecio, 'id'>;

type ListaPrecioFormGroupContent = {
  id: FormControl<IListaPrecio['id'] | NewListaPrecio['id']>;
  name: FormControl<IListaPrecio['name']>;
  date: FormControl<IListaPrecio['date']>;
  proveedor: FormControl<IListaPrecio['proveedor']>;
};

export type ListaPrecioFormGroup = FormGroup<ListaPrecioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ListaPrecioFormService {
  createListaPrecioFormGroup(listaPrecio: ListaPrecioFormGroupInput = { id: null }): ListaPrecioFormGroup {
    const listaPrecioRawValue = {
      ...this.getFormDefaults(),
      ...listaPrecio,
    };
    return new FormGroup<ListaPrecioFormGroupContent>({
      id: new FormControl(
        { value: listaPrecioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(listaPrecioRawValue.name, {
        validators: [Validators.required],
      }),
      date: new FormControl(listaPrecioRawValue.date),
      proveedor: new FormControl(listaPrecioRawValue.proveedor),
    });
  }

  getListaPrecio(form: ListaPrecioFormGroup): IListaPrecio | NewListaPrecio {
    return form.getRawValue() as IListaPrecio | NewListaPrecio;
  }

  resetForm(form: ListaPrecioFormGroup, listaPrecio: ListaPrecioFormGroupInput): void {
    const listaPrecioRawValue = { ...this.getFormDefaults(), ...listaPrecio };
    form.reset(
      {
        ...listaPrecioRawValue,
        id: { value: listaPrecioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ListaPrecioFormDefaults {
    return {
      id: null,
    };
  }
}
