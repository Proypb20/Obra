import dayjs from 'dayjs/esm';

import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';

import { ITransaccion, NewTransaccion } from './transaccion.model';

export const sampleWithRequiredData: ITransaccion = {
  id: 86269,
};

export const sampleWithPartialData: ITransaccion = {
  id: 83491,
  date: dayjs('2022-09-12'),
  transactionNumber: 'Datos AGP Palladium',
  amount: 79075,
  note: 'Money',
};

export const sampleWithFullData: ITransaccion = {
  id: 59966,
  date: dayjs('2022-09-12'),
  paymentMethod: MetodoPago['Banco'],
  transactionNumber: 'Región',
  amount: 9138,
  note: 'Granito CSS definición',
};

export const sampleWithNewData: NewTransaccion = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
