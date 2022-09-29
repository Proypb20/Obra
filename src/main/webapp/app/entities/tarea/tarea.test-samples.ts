import { ITarea, NewTarea } from './tarea.model';

export const sampleWithRequiredData: ITarea = {
  id: 58845,
  name: 'Moda',
};

export const sampleWithPartialData: ITarea = {
  id: 7169,
  name: 'Edificio Bebes Marca',
  quantity: 90242,
  cost: 58848,
  advanceStatus: 85,
};

export const sampleWithFullData: ITarea = {
  id: 38861,
  name: 'panel Violeta Account',
  quantity: 12860,
  cost: 3745,
  advanceStatus: 1,
};

export const sampleWithNewData: NewTarea = {
  name: 'Configurable convergence',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
