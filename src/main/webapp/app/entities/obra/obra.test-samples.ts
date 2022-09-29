import dayjs from 'dayjs/esm';

import { IObra, NewObra } from './obra.model';

export const sampleWithRequiredData: IObra = {
  id: 75164,
  name: 'Práctico Cuentas',
  comments: 'comments',
};

export const sampleWithPartialData: IObra = {
  id: 15616,
  name: 'Baleares',
  city: 'Manuelafurt',
  comments: 'Comments',
};

export const sampleWithFullData: IObra = {
  id: 75831,
  name: 'Market Amarillo',
  address: 'firmware Uruguay',
  city: 'La Coruña Maríamouth',
  comments: 'comments',
};

export const sampleWithNewData: NewObra = {
  name: 'Buckinghamshire Calleja XSS',
  comments: 'comments',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
