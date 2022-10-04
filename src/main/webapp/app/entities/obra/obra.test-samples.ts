import { IObra, NewObra } from './obra.model';

export const sampleWithRequiredData: IObra = {
  id: 75164,
  name: 'Práctico Cuentas',
};

export const sampleWithPartialData: IObra = {
  id: 70519,
  name: 'base',
};

export const sampleWithFullData: IObra = {
  id: 5189,
  name: 'bluetooth Rampa',
  address: 'global Comunidad',
  city: 'Alcoy José Eduardo',
  comments: 'Programa invoice',
};

export const sampleWithNewData: NewObra = {
  name: 'Salud wireless Hormigon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
