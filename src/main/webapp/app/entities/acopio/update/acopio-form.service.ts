import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_FORMAT } from 'app/config/input.constants';
import { IAcopio, NewAcopio } from '../acopio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAcopio for edit and NewAcopioFormGroupInput for create.
 */
type AcopioFormGroupInput = IAcopio | PartialWithRequiredKeyOf<NewAcopio>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAcopio | NewAcopio> = Omit<T, 'date'> & {
  date?: string | null;
};

type AcopioFormRawValue = FormValueOf<IAcopio>;

type NewAcopioFormRawValue = FormValueOf<NewAcopio>;

type AcopioFormDefaults = Pick<NewAcopio, 'id' | 'date'>;

type AcopioFormGroupContent = {
  id: FormControl<AcopioFormRawValue['id'] | NewAcopio['id']>;
  date: FormControl<AcopioFormRawValue['date']>;
  totalAmount: FormControl<AcopioFormRawValue['totalAmount']>;
  saldo: FormControl<AcopioFormRawValue['saldo']>;
  obra: FormControl<AcopioFormRawValue['obra']>;
  listaprecio: FormControl<AcopioFormRawValue['listaprecio']>;
  proveedor: FormControl<AcopioFormRawValue['proveedor']>;
};

export type AcopioFormGroup = FormGroup<AcopioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AcopioFormService {
  createAcopioFormGroup(acopio: AcopioFormGroupInput = { id: null }): AcopioFormGroup {
    const acopioRawValue = this.convertAcopioToAcopioRawValue({
      ...this.getFormDefaults(),
      ...acopio,
    });
    return new FormGroup<AcopioFormGroupContent>({
      id: new FormControl(
        { value: acopioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      date: new FormControl(acopioRawValue.date),
      totalAmount: new FormControl(acopioRawValue.totalAmount),
      saldo: new FormControl(acopioRawValue.saldo),
      obra: new FormControl(acopioRawValue.obra),
      listaprecio: new FormControl(acopioRawValue.listaprecio),
      proveedor: new FormControl(acopioRawValue.proveedor),
    });
  }

  getAcopio(form: AcopioFormGroup): IAcopio | NewAcopio {
    return this.convertAcopioRawValueToAcopio(form.getRawValue() as AcopioFormRawValue | NewAcopioFormRawValue);
  }

  resetForm(form: AcopioFormGroup, acopio: AcopioFormGroupInput): void {
    const acopioRawValue = this.convertAcopioToAcopioRawValue({ ...this.getFormDefaults(), ...acopio });
    form.reset(
      {
        ...acopioRawValue,
        id: { value: acopioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AcopioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertAcopioRawValueToAcopio(rawAcopio: AcopioFormRawValue | NewAcopioFormRawValue): IAcopio | NewAcopio {
    return {
      ...rawAcopio,
      date: dayjs(rawAcopio.date, DATE_FORMAT),
    };
  }

  private convertAcopioToAcopioRawValue(
    acopio: IAcopio | (Partial<NewAcopio> & AcopioFormDefaults)
  ): AcopioFormRawValue | PartialWithRequiredKeyOf<NewAcopioFormRawValue> {
    return {
      ...acopio,
      date: acopio.date ? acopio.date.format(DATE_FORMAT) : undefined,
    };
  }
}
