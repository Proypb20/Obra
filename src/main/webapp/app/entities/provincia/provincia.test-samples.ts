import { IProvincia, NewProvincia } from './provincia.model';

export const sampleWithRequiredData: IProvincia = {
  id: 78531,
  name: 'Camiseta Masía Buckinghamshire',
};

export const sampleWithPartialData: IProvincia = {
  id: 5107,
  name: 'Barrio',
};

export const sampleWithFullData: IProvincia = {
  id: 80622,
  name: 'Loan metrics',
};

export const sampleWithNewData: NewProvincia = {
  name: 'Ingeniero',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
