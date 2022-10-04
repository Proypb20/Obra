import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITipoComprobante, NewTipoComprobante } from '../tipo-comprobante.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITipoComprobante for edit and NewTipoComprobanteFormGroupInput for create.
 */
type TipoComprobanteFormGroupInput = ITipoComprobante | PartialWithRequiredKeyOf<NewTipoComprobante>;

type TipoComprobanteFormDefaults = Pick<NewTipoComprobante, 'id'>;

type TipoComprobanteFormGroupContent = {
  id: FormControl<ITipoComprobante['id'] | NewTipoComprobante['id']>;
  name: FormControl<ITipoComprobante['name']>;
};

export type TipoComprobanteFormGroup = FormGroup<TipoComprobanteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TipoComprobanteFormService {
  createTipoComprobanteFormGroup(tipoComprobante: TipoComprobanteFormGroupInput = { id: null }): TipoComprobanteFormGroup {
    const tipoComprobanteRawValue = {
      ...this.getFormDefaults(),
      ...tipoComprobante,
    };
    return new FormGroup<TipoComprobanteFormGroupContent>({
      id: new FormControl(
        { value: tipoComprobanteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(tipoComprobanteRawValue.name, {
        validators: [Validators.required],
      }),
    });
  }

  getTipoComprobante(form: TipoComprobanteFormGroup): ITipoComprobante | NewTipoComprobante {
    return form.getRawValue() as ITipoComprobante | NewTipoComprobante;
  }

  resetForm(form: TipoComprobanteFormGroup, tipoComprobante: TipoComprobanteFormGroupInput): void {
    const tipoComprobanteRawValue = { ...this.getFormDefaults(), ...tipoComprobante };
    form.reset(
      {
        ...tipoComprobanteRawValue,
        id: { value: tipoComprobanteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TipoComprobanteFormDefaults {
    return {
      id: null,
    };
  }
}
