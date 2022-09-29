import dayjs from 'dayjs/esm';

import { Estado } from 'app/entities/enumerations/estado.model';

import { IDetalleAcopio, NewDetalleAcopio } from './detalle-acopio.model';

export const sampleWithRequiredData: IDetalleAcopio = {
  id: 56056,
  quantity: 86068,
  unitPrice: 14865,
  amount: 24980,
  requestDate: dayjs('2022-09-12'),
};

export const sampleWithPartialData: IDetalleAcopio = {
  id: 87657,
  date: dayjs('2022-09-12'),
  quantity: 35102,
  unitPrice: 64973,
  amount: 61099,
  requestDate: dayjs('2022-09-12'),
};

export const sampleWithFullData: IDetalleAcopio = {
  id: 98753,
  date: dayjs('2022-09-12'),
  description: 'Hormigon Tácticas',
  quantity: 16446,
  unitPrice: 19093,
  amount: 43909,
  requestDate: dayjs('2022-09-12'),
  promiseDate: dayjs('2022-09-12'),
  deliveryStatus: Estado['Pendiente'],
};

export const sampleWithNewData: NewDetalleAcopio = {
  quantity: 78518,
  unitPrice: 13231,
  amount: 45190,
  requestDate: dayjs('2022-09-12'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
