import dayjs from 'dayjs/esm';

import { IAcopio, NewAcopio } from './acopio.model';

export const sampleWithRequiredData: IAcopio = {
  id: 6421,
};

export const sampleWithPartialData: IAcopio = {
  id: 98975,
};

export const sampleWithFullData: IAcopio = {
  id: 21394,
  date: dayjs('2022-09-12T09:43'),
  totalAmount: 69536,
  saldo: 60926,
};

export const sampleWithNewData: NewAcopio = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
