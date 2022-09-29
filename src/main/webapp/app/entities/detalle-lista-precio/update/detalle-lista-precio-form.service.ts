import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDetalleListaPrecio, NewDetalleListaPrecio } from '../detalle-lista-precio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDetalleListaPrecio for edit and NewDetalleListaPrecioFormGroupInput for create.
 */
type DetalleListaPrecioFormGroupInput = IDetalleListaPrecio | PartialWithRequiredKeyOf<NewDetalleListaPrecio>;

type DetalleListaPrecioFormDefaults = Pick<NewDetalleListaPrecio, 'id'>;

type DetalleListaPrecioFormGroupContent = {
  id: FormControl<IDetalleListaPrecio['id'] | NewDetalleListaPrecio['id']>;
  code: FormControl<IDetalleListaPrecio['code']>;
  product: FormControl<IDetalleListaPrecio['product']>;
  amount: FormControl<IDetalleListaPrecio['amount']>;
  listaPrecio: FormControl<IDetalleListaPrecio['listaPrecio']>;
};

export type DetalleListaPrecioFormGroup = FormGroup<DetalleListaPrecioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DetalleListaPrecioFormService {
  createDetalleListaPrecioFormGroup(detalleListaPrecio: DetalleListaPrecioFormGroupInput = { id: null }): DetalleListaPrecioFormGroup {
    const detalleListaPrecioRawValue = {
      ...this.getFormDefaults(),
      ...detalleListaPrecio,
    };
    return new FormGroup<DetalleListaPrecioFormGroupContent>({
      id: new FormControl(
        { value: detalleListaPrecioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(detalleListaPrecioRawValue.code),
      product: new FormControl(detalleListaPrecioRawValue.product),
      amount: new FormControl(detalleListaPrecioRawValue.amount),
      listaPrecio: new FormControl(detalleListaPrecioRawValue.listaPrecio),
    });
  }

  getDetalleListaPrecio(form: DetalleListaPrecioFormGroup): IDetalleListaPrecio | NewDetalleListaPrecio {
    return form.getRawValue() as IDetalleListaPrecio | NewDetalleListaPrecio;
  }

  resetForm(form: DetalleListaPrecioFormGroup, detalleListaPrecio: DetalleListaPrecioFormGroupInput): void {
    const detalleListaPrecioRawValue = { ...this.getFormDefaults(), ...detalleListaPrecio };
    form.reset(
      {
        ...detalleListaPrecioRawValue,
        id: { value: detalleListaPrecioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DetalleListaPrecioFormDefaults {
    return {
      id: null,
    };
  }
}
