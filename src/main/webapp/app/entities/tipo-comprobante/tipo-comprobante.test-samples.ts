import { ITipoComprobante, NewTipoComprobante } from './tipo-comprobante.model';

export const sampleWithRequiredData: ITipoComprobante = {
  id: 51947,
  name: 'de Canarias',
};

export const sampleWithPartialData: ITipoComprobante = {
  id: 74379,
  name: 'Cambridgeshire',
};

export const sampleWithFullData: ITipoComprobante = {
  id: 51079,
  name: 'Personal',
};

export const sampleWithNewData: NewTipoComprobante = {
  name: 'compressing moderador',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
