import { ITipoComprobante, NewTipoComprobante } from './tipo-comprobante.model';

export const sampleWithRequiredData: ITipoComprobante = {
  id: 51947,
  name: 'de Canarias',
  sign: '-',
};

export const sampleWithPartialData: ITipoComprobante = {
  id: 74379,
  name: 'Cambridgeshire',
  sign: '-',
};

export const sampleWithFullData: ITipoComprobante = {
  id: 51079,
  name: 'Personal',
  sign: '-',
};

export const sampleWithNewData: NewTipoComprobante = {
  name: 'compressing moderador',
  sign: '-',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
