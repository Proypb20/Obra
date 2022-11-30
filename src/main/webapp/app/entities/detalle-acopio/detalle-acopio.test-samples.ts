import dayjs from 'dayjs/esm';

import { Estado } from 'app/entities/enumerations/estado.model';

import { IDetalleAcopio, NewDetalleAcopio } from './detalle-acopio.model';

export const sampleWithRequiredData: IDetalleAcopio = {
  id: 56056,
  quantity: 86068,
  unitPrice: 14865,
  amount: 24980,
  requestDate: dayjs('2022-09-12T10:16'),
};

export const sampleWithPartialData: IDetalleAcopio = {
  id: 87657,
  date: dayjs('2022-09-12T18:54'),
  quantity: 35102,
  unitPrice: 64973,
  amount: 61099,
  requestDate: dayjs('2022-09-12T18:23'),
};

export const sampleWithFullData: IDetalleAcopio = {
  id: 98753,
  date: dayjs('2022-09-12T12:32'),
  description: 'Hormigon Tácticas',
  quantity: 16446,
  unitPrice: 19093,
  amount: 43909,
  requestDate: dayjs('2022-09-12T21:18'),
  promiseDate: dayjs('2022-09-12T07:20'),
  deliveryStatus: Estado['Pendiente'],
};

export const sampleWithNewData: NewDetalleAcopio = {
  quantity: 78518,
  unitPrice: 13231,
  amount: 45190,
  requestDate: dayjs('2022-09-12T09:57'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
