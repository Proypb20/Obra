import { IConcepto, NewConcepto } from './concepto.model';

export const sampleWithRequiredData: IConcepto = {
  id: 26273,
  name: 'benficios transparent',
};

export const sampleWithPartialData: IConcepto = {
  id: 24368,
  name: 'hack',
};

export const sampleWithFullData: IConcepto = {
  id: 53247,
  name: 'hack hardware Violeta',
};

export const sampleWithNewData: NewConcepto = {
  name: 'Norwegian',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
