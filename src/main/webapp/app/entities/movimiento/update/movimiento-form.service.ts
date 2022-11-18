import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_FORMAT } from 'app/config/input.constants';
import { IMovimiento, NewMovimiento } from '../movimiento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMovimiento for edit and NewMovimientoFormGroupInput for create.
 */
type MovimientoFormGroupInput = IMovimiento | PartialWithRequiredKeyOf<NewMovimiento>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMovimiento | NewMovimiento> = Omit<T, 'date'> & {
  date?: string | null;
};

type MovimientoFormRawValue = FormValueOf<IMovimiento>;

type NewMovimientoFormRawValue = FormValueOf<NewMovimiento>;

type MovimientoFormDefaults = Pick<NewMovimiento, 'id' | 'date'>;

type MovimientoFormGroupContent = {
  id: FormControl<MovimientoFormRawValue['id'] | NewMovimiento['id']>;
  date: FormControl<MovimientoFormRawValue['date']>;
  description: FormControl<MovimientoFormRawValue['description']>;
  metodoPago: FormControl<MovimientoFormRawValue['metodoPago']>;
  amount: FormControl<MovimientoFormRawValue['amount']>;
  transactionNumber: FormControl<MovimientoFormRawValue['transactionNumber']>;
  obra: FormControl<MovimientoFormRawValue['obra']>;
  subcontratista: FormControl<MovimientoFormRawValue['subcontratista']>;
  concepto: FormControl<MovimientoFormRawValue['concepto']>;
  tipoComprobante: FormControl<MovimientoFormRawValue['tipoComprobante']>;
};

export type MovimientoFormGroup = FormGroup<MovimientoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MovimientoFormService {
  createMovimientoFormGroup(movimiento: MovimientoFormGroupInput = { id: null }): MovimientoFormGroup {
    const movimientoRawValue = this.convertMovimientoToMovimientoRawValue({
      ...this.getFormDefaults(),
      ...movimiento,
    });
    return new FormGroup<MovimientoFormGroupContent>({
      id: new FormControl(
        { value: movimientoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      date: new FormControl(movimientoRawValue.date, {
        validators: [Validators.required],
      }),
      description: new FormControl(movimientoRawValue.description, {
        validators: [Validators.required],
      }),
      metodoPago: new FormControl(movimientoRawValue.metodoPago, {
        validators: [Validators.required],
      }),
      amount: new FormControl(movimientoRawValue.amount, {
        validators: [Validators.required],
      }),
      transactionNumber: new FormControl(movimientoRawValue.transactionNumber),
      obra: new FormControl(movimientoRawValue.obra),
      subcontratista: new FormControl(movimientoRawValue.subcontratista),
      concepto: new FormControl(movimientoRawValue.concepto),
      tipoComprobante: new FormControl(movimientoRawValue.tipoComprobante),
    });
  }

  getMovimiento(form: MovimientoFormGroup): IMovimiento | NewMovimiento {
    return this.convertMovimientoRawValueToMovimiento(form.getRawValue() as MovimientoFormRawValue | NewMovimientoFormRawValue);
  }

  resetForm(form: MovimientoFormGroup, movimiento: MovimientoFormGroupInput): void {
    const movimientoRawValue = this.convertMovimientoToMovimientoRawValue({ ...this.getFormDefaults(), ...movimiento });
    form.reset(
      {
        ...movimientoRawValue,
        id: { value: movimientoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MovimientoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertMovimientoRawValueToMovimiento(
    rawMovimiento: MovimientoFormRawValue | NewMovimientoFormRawValue
  ): IMovimiento | NewMovimiento {
    return {
      ...rawMovimiento,
      date: dayjs(rawMovimiento.date, DATE_FORMAT),
    };
  }

  private convertMovimientoToMovimientoRawValue(
    movimiento: IMovimiento | (Partial<NewMovimiento> & MovimientoFormDefaults)
  ): MovimientoFormRawValue | PartialWithRequiredKeyOf<NewMovimientoFormRawValue> {
    return {
      ...movimiento,
      date: movimiento.date ? movimiento.date.format(DATE_FORMAT) : undefined,
    };
  }
}
