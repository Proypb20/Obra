import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

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

type MovimientoFormDefaults = Pick<NewMovimiento, 'id'>;

type MovimientoFormGroupContent = {
  id: FormControl<IMovimiento['id'] | NewMovimiento['id']>;
  date: FormControl<IMovimiento['date']>;
  description: FormControl<IMovimiento['description']>;
  metodoPago: FormControl<IMovimiento['metodoPago']>;
  amount: FormControl<IMovimiento['amount']>;
  obra: FormControl<IMovimiento['obra']>;
  subcontratista: FormControl<IMovimiento['subcontratista']>;
  concepto: FormControl<IMovimiento['concepto']>;
};

export type MovimientoFormGroup = FormGroup<MovimientoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MovimientoFormService {
  createMovimientoFormGroup(movimiento: MovimientoFormGroupInput = { id: null }): MovimientoFormGroup {
    const movimientoRawValue = {
      ...this.getFormDefaults(),
      ...movimiento,
    };
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
      obra: new FormControl(movimientoRawValue.obra),
      subcontratista: new FormControl(movimientoRawValue.subcontratista),
      concepto: new FormControl(movimientoRawValue.concepto),
    });
  }

  getMovimiento(form: MovimientoFormGroup): IMovimiento | NewMovimiento {
    return form.getRawValue() as IMovimiento | NewMovimiento;
  }

  resetForm(form: MovimientoFormGroup, movimiento: MovimientoFormGroupInput): void {
    const movimientoRawValue = { ...this.getFormDefaults(), ...movimiento };
    form.reset(
      {
        ...movimientoRawValue,
        id: { value: movimientoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MovimientoFormDefaults {
    return {
      id: null,
    };
  }
}
