import dayjs from 'dayjs/esm';

import { IAcopio, NewAcopio } from './acopio.model';

export const sampleWithRequiredData: IAcopio = {
  id: 6421,
};

export const sampleWithPartialData: IAcopio = {
  id: 2444,
};

export const sampleWithFullData: IAcopio = {
  id: 98975,
  date: dayjs('2022-09-12'),
  totalAmount: 66774,
};

export const sampleWithNewData: NewAcopio = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
