import dayjs from 'dayjs/esm';

import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';

import { IMovimiento, NewMovimiento } from './movimiento.model';

export const sampleWithRequiredData: IMovimiento = {
  id: 73821,
  date: dayjs('2022-10-12'),
  description: 'Kwanza tiempo',
  metodoPago: MetodoPago['BANCO'],
  amount: 58255,
};

export const sampleWithPartialData: IMovimiento = {
  id: 75956,
  date: dayjs('2022-10-12'),
  description: 'Kuwait',
  metodoPago: MetodoPago['BANCO'],
  amount: 34391,
};

export const sampleWithFullData: IMovimiento = {
  id: 4978,
  date: dayjs('2022-10-12'),
  description: 'añadido Gerente',
  metodoPago: MetodoPago['EFECTIVO'],
  amount: 99630,
};

export const sampleWithNewData: NewMovimiento = {
  date: dayjs('2022-10-12'),
  description: 'override',
  metodoPago: MetodoPago['EFECTIVO'],
  amount: 8647,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
