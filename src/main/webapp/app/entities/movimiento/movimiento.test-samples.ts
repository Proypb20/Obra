import dayjs from 'dayjs/esm';

import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';

import { IMovimiento, NewMovimiento } from './movimiento.model';

export const sampleWithRequiredData: IMovimiento = {
  id: 73821,
  date: dayjs('2022-10-12T02:03'),
  description: 'Kwanza tiempo',
  metodoPago: MetodoPago['Banco'],
  amount: 58255,
};

export const sampleWithPartialData: IMovimiento = {
  id: 24380,
  date: dayjs('2022-10-12T21:22'),
  description: 'Berkshire proactive',
  metodoPago: MetodoPago['Efectivo'],
  amount: 26868,
  transactionNumber: 'Vasco navigating Increible',
};

export const sampleWithFullData: IMovimiento = {
  id: 47766,
  date: dayjs('2022-10-12T23:16'),
  description: 'Colegio Guapo Creativo',
  metodoPago: MetodoPago['Banco'],
  amount: 53127,
  transactionNumber: 'auxiliary',
};

export const sampleWithNewData: NewMovimiento = {
  date: dayjs('2022-10-12T14:51'),
  description: 'Groenlandia Cine',
  metodoPago: MetodoPago['Banco'],
  amount: 58098,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
