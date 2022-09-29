import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITransaccion, NewTransaccion } from '../transaccion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITransaccion for edit and NewTransaccionFormGroupInput for create.
 */
type TransaccionFormGroupInput = ITransaccion | PartialWithRequiredKeyOf<NewTransaccion>;

type TransaccionFormDefaults = Pick<NewTransaccion, 'id'>;

type TransaccionFormGroupContent = {
  id: FormControl<ITransaccion['id'] | NewTransaccion['id']>;
  date: FormControl<ITransaccion['date']>;
  paymentMethod: FormControl<ITransaccion['paymentMethod']>;
  transactionNumber: FormControl<ITransaccion['transactionNumber']>;
  amount: FormControl<ITransaccion['amount']>;
  note: FormControl<ITransaccion['note']>;
  obra: FormControl<ITransaccion['obra']>;
  subcontratista: FormControl<ITransaccion['subcontratista']>;
  tipoComprobante: FormControl<ITransaccion['tipoComprobante']>;
  concepto: FormControl<ITransaccion['concepto']>;
};

export type TransaccionFormGroup = FormGroup<TransaccionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TransaccionFormService {
  createTransaccionFormGroup(transaccion: TransaccionFormGroupInput = { id: null }): TransaccionFormGroup {
    const transaccionRawValue = {
      ...this.getFormDefaults(),
      ...transaccion,
    };
    return new FormGroup<TransaccionFormGroupContent>({
      id: new FormControl(
        { value: transaccionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      date: new FormControl(transaccionRawValue.date),
      paymentMethod: new FormControl(transaccionRawValue.paymentMethod),
      transactionNumber: new FormControl(transaccionRawValue.transactionNumber),
      amount: new FormControl(transaccionRawValue.amount),
      note: new FormControl(transaccionRawValue.note),
      obra: new FormControl(transaccionRawValue.obra),
      subcontratista: new FormControl(transaccionRawValue.subcontratista),
      tipoComprobante: new FormControl(transaccionRawValue.tipoComprobante),
      concepto: new FormControl(transaccionRawValue.concepto),
    });
  }

  getTransaccion(form: TransaccionFormGroup): ITransaccion | NewTransaccion {
    return form.getRawValue() as ITransaccion | NewTransaccion;
  }

  resetForm(form: TransaccionFormGroup, transaccion: TransaccionFormGroupInput): void {
    const transaccionRawValue = { ...this.getFormDefaults(), ...transaccion };
    form.reset(
      {
        ...transaccionRawValue,
        id: { value: transaccionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TransaccionFormDefaults {
    return {
      id: null,
    };
  }
}
