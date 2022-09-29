import dayjs from 'dayjs/esm';

import { IListaPrecio, NewListaPrecio } from './lista-precio.model';

export const sampleWithRequiredData: IListaPrecio = {
  id: 30761,
  name: 'deposit',
};

export const sampleWithPartialData: IListaPrecio = {
  id: 79676,
  name: 'Guapo RAM Salvador',
};

export const sampleWithFullData: IListaPrecio = {
  id: 35797,
  name: 'Explanada hard holistic',
  date: dayjs('2022-09-19'),
};

export const sampleWithNewData: NewListaPrecio = {
  name: 'digital Funcionalidad Regional',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
