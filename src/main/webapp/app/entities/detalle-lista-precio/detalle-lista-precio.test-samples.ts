import { IDetalleListaPrecio, NewDetalleListaPrecio } from './detalle-lista-precio.model';

export const sampleWithRequiredData: IDetalleListaPrecio = {
  id: 92710,
};

export const sampleWithPartialData: IDetalleListaPrecio = {
  id: 9426,
  code: 'bandwidth',
  product: 'XML',
  amount: 94182,
};

export const sampleWithFullData: IDetalleListaPrecio = {
  id: 67343,
  code: 'paralelismo',
  product: 'input Sincronizado hacking',
  amount: 58822,
};

export const sampleWithNewData: NewDetalleListaPrecio = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
