import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_FORMAT } from 'app/config/input.constants';
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

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IListaPrecio | NewListaPrecio> = Omit<T, 'date'> & {
  date?: string | null;
};

type ListaPrecioFormRawValue = FormValueOf<IListaPrecio>;

type NewListaPrecioFormRawValue = FormValueOf<NewListaPrecio>;

type ListaPrecioFormDefaults = Pick<NewListaPrecio, 'id' | 'date'>;

type ListaPrecioFormGroupContent = {
  id: FormControl<ListaPrecioFormRawValue['id'] | NewListaPrecio['id']>;
  name: FormControl<ListaPrecioFormRawValue['name']>;
  date: FormControl<ListaPrecioFormRawValue['date']>;
  proveedor: FormControl<ListaPrecioFormRawValue['proveedor']>;
};

export type ListaPrecioFormGroup = FormGroup<ListaPrecioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ListaPrecioFormService {
  createListaPrecioFormGroup(listaPrecio: ListaPrecioFormGroupInput = { id: null }): ListaPrecioFormGroup {
    const listaPrecioRawValue = this.convertListaPrecioToListaPrecioRawValue({
      ...this.getFormDefaults(),
      ...listaPrecio,
    });
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
    return this.convertListaPrecioRawValueToListaPrecio(form.getRawValue() as ListaPrecioFormRawValue | NewListaPrecioFormRawValue);
  }

  resetForm(form: ListaPrecioFormGroup, listaPrecio: ListaPrecioFormGroupInput): void {
    const listaPrecioRawValue = this.convertListaPrecioToListaPrecioRawValue({ ...this.getFormDefaults(), ...listaPrecio });
    form.reset(
      {
        ...listaPrecioRawValue,
        id: { value: listaPrecioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ListaPrecioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertListaPrecioRawValueToListaPrecio(
    rawListaPrecio: ListaPrecioFormRawValue | NewListaPrecioFormRawValue
  ): IListaPrecio | NewListaPrecio {
    return {
      ...rawListaPrecio,
      date: dayjs(rawListaPrecio.date, DATE_FORMAT),
    };
  }

  private convertListaPrecioToListaPrecioRawValue(
    listaPrecio: IListaPrecio | (Partial<NewListaPrecio> & ListaPrecioFormDefaults)
  ): ListaPrecioFormRawValue | PartialWithRequiredKeyOf<NewListaPrecioFormRawValue> {
    return {
      ...listaPrecio,
      date: listaPrecio.date ? listaPrecio.date.format(DATE_FORMAT) : undefined,
    };
  }
}
