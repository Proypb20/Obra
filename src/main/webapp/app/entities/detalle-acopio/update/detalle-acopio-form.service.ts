import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDetalleAcopio, NewDetalleAcopio } from '../detalle-acopio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDetalleAcopio for edit and NewDetalleAcopioFormGroupInput for create.
 */
type DetalleAcopioFormGroupInput = IDetalleAcopio | PartialWithRequiredKeyOf<NewDetalleAcopio>;

type DetalleAcopioFormDefaults = Pick<NewDetalleAcopio, 'id'>;

type DetalleAcopioFormGroupContent = {
  id: FormControl<IDetalleAcopio['id'] | NewDetalleAcopio['id']>;
  date: FormControl<IDetalleAcopio['date']>;
  description: FormControl<IDetalleAcopio['description']>;
  quantity: FormControl<IDetalleAcopio['quantity']>;
  unitPrice: FormControl<IDetalleAcopio['unitPrice']>;
  amount: FormControl<IDetalleAcopio['amount']>;
  requestDate: FormControl<IDetalleAcopio['requestDate']>;
  promiseDate: FormControl<IDetalleAcopio['promiseDate']>;
  deliveryStatus: FormControl<IDetalleAcopio['deliveryStatus']>;
  acopio: FormControl<IDetalleAcopio['acopio']>;
};

export type DetalleAcopioFormGroup = FormGroup<DetalleAcopioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DetalleAcopioFormService {
  createDetalleAcopioFormGroup(detalleAcopio: DetalleAcopioFormGroupInput = { id: null }): DetalleAcopioFormGroup {
    const detalleAcopioRawValue = {
      ...this.getFormDefaults(),
      ...detalleAcopio,
    };
    return new FormGroup<DetalleAcopioFormGroupContent>({
      id: new FormControl(
        { value: detalleAcopioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      date: new FormControl(detalleAcopioRawValue.date),
      description: new FormControl(detalleAcopioRawValue.description),
      quantity: new FormControl(detalleAcopioRawValue.quantity, {
        validators: [Validators.required],
      }),
      unitPrice: new FormControl(detalleAcopioRawValue.unitPrice, {
        validators: [Validators.required],
      }),
      amount: new FormControl(detalleAcopioRawValue.amount, {
        validators: [Validators.required],
      }),
      requestDate: new FormControl(detalleAcopioRawValue.requestDate, {
        validators: [Validators.required],
      }),
      promiseDate: new FormControl(detalleAcopioRawValue.promiseDate),
      deliveryStatus: new FormControl(detalleAcopioRawValue.deliveryStatus),
      acopio: new FormControl(detalleAcopioRawValue.acopio),
    });
  }

  getDetalleAcopio(form: DetalleAcopioFormGroup): IDetalleAcopio | NewDetalleAcopio {
    return form.getRawValue() as IDetalleAcopio | NewDetalleAcopio;
  }

  resetForm(form: DetalleAcopioFormGroup, detalleAcopio: DetalleAcopioFormGroupInput): void {
    const detalleAcopioRawValue = { ...this.getFormDefaults(), ...detalleAcopio };
    form.reset(
      {
        ...detalleAcopioRawValue,
        id: { value: detalleAcopioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DetalleAcopioFormDefaults {
    return {
      id: null,
    };
  }
}
