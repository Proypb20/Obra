import { IUnidadMedida, NewUnidadMedida } from './unidad-medida.model';

export const sampleWithRequiredData: IUnidadMedida = {
  id: 39685,
  name: 'parsing Inversor pixel',
};

export const sampleWithPartialData: IUnidadMedida = {
  id: 90896,
  name: 'one-to-one',
};

export const sampleWithFullData: IUnidadMedida = {
  id: 53737,
  name: 'UIC-Franc deposit',
};

export const sampleWithNewData: NewUnidadMedida = {
  name: 'Calleja',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
