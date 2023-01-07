import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_FORMAT } from 'app/config/input.constants';
import { Estado } from 'app/entities/enumerations/estado.model';
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

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDetalleAcopio | NewDetalleAcopio> = Omit<T, 'date' | 'requestDate' | 'promiseDate'> & {
  date?: string | null;
  requestDate?: string | null;
  promiseDate?: string | null;
};

type DetalleAcopioFormRawValue = FormValueOf<IDetalleAcopio>;

type NewDetalleAcopioFormRawValue = FormValueOf<NewDetalleAcopio>;

type DetalleAcopioFormDefaults = Pick<NewDetalleAcopio, 'id' | 'date' | 'requestDate' | 'promiseDate' | 'deliveryStatus'>;

type DetalleAcopioFormGroupContent = {
  id: FormControl<DetalleAcopioFormRawValue['id'] | NewDetalleAcopio['id']>;
  date: FormControl<DetalleAcopioFormRawValue['date']>;
  description: FormControl<DetalleAcopioFormRawValue['description']>;
  quantity: FormControl<DetalleAcopioFormRawValue['quantity']>;
  unitPrice: FormControl<DetalleAcopioFormRawValue['unitPrice']>;
  amount: FormControl<DetalleAcopioFormRawValue['amount']>;
  requestDate: FormControl<DetalleAcopioFormRawValue['requestDate']>;
  promiseDate: FormControl<DetalleAcopioFormRawValue['promiseDate']>;
  deliveryStatus: FormControl<DetalleAcopioFormRawValue['deliveryStatus']>;
  acopio: FormControl<DetalleAcopioFormRawValue['acopio']>;
  detalleListaPrecio: FormControl<DetalleAcopioFormRawValue['detalleListaPrecio']>;
};

export type DetalleAcopioFormGroup = FormGroup<DetalleAcopioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DetalleAcopioFormService {
  createDetalleAcopioFormGroup(detalleAcopio: DetalleAcopioFormGroupInput = { id: null }): DetalleAcopioFormGroup {
    const detalleAcopioRawValue = this.convertDetalleAcopioToDetalleAcopioRawValue({
      ...this.getFormDefaults(),
      ...detalleAcopio,
    });
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
      detalleListaPrecio: new FormControl(detalleAcopioRawValue.detalleListaPrecio),
    });
  }

  getDetalleAcopio(form: DetalleAcopioFormGroup): IDetalleAcopio | NewDetalleAcopio {
    return this.convertDetalleAcopioRawValueToDetalleAcopio(form.getRawValue() as DetalleAcopioFormRawValue | NewDetalleAcopioFormRawValue);
  }

  resetForm(form: DetalleAcopioFormGroup, detalleAcopio: DetalleAcopioFormGroupInput): void {
    const detalleAcopioRawValue = this.convertDetalleAcopioToDetalleAcopioRawValue({ ...this.getFormDefaults(), ...detalleAcopio });
    form.reset(
      {
        ...detalleAcopioRawValue,
        id: { value: detalleAcopioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DetalleAcopioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
      requestDate: currentTime,
      promiseDate: currentTime,
      deliveryStatus: Estado.Pendiente,
    };
  }

  private convertDetalleAcopioRawValueToDetalleAcopio(
    rawDetalleAcopio: DetalleAcopioFormRawValue | NewDetalleAcopioFormRawValue
  ): IDetalleAcopio | NewDetalleAcopio {
    return {
      ...rawDetalleAcopio,
      date: dayjs(rawDetalleAcopio.date, DATE_FORMAT),
      requestDate: dayjs(rawDetalleAcopio.requestDate, DATE_FORMAT),
      promiseDate: dayjs(rawDetalleAcopio.promiseDate, DATE_FORMAT),
      deliveryStatus: rawDetalleAcopio.deliveryStatus,
    };
  }

  private convertDetalleAcopioToDetalleAcopioRawValue(
    detalleAcopio: IDetalleAcopio | (Partial<NewDetalleAcopio> & DetalleAcopioFormDefaults)
  ): DetalleAcopioFormRawValue | PartialWithRequiredKeyOf<NewDetalleAcopioFormRawValue> {
    return {
      ...detalleAcopio,
      date: detalleAcopio.date ? detalleAcopio.date.format(DATE_FORMAT) : undefined,
      requestDate: detalleAcopio.requestDate ? detalleAcopio.requestDate.format(DATE_FORMAT) : undefined,
      promiseDate: detalleAcopio.promiseDate ? detalleAcopio.promiseDate.format(DATE_FORMAT) : undefined,
    };
  }
}
