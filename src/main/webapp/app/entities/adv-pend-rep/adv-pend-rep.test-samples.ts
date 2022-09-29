import { IAdvPendRep, NewAdvPendRep } from './adv-pend-rep.model';

export const sampleWithRequiredData: IAdvPendRep = {
  id: 26273,
  obra: 'benficios transparent',
  subcontratista: 'Sub1',
  advance_status: 1,
};

export const sampleWithPartialData: IAdvPendRep = {
  id: 24368,
  obra: 'benficios transparent',
  subcontratista: 'Sub1',
  advance_status: 1,
};

export const sampleWithFullData: IAdvPendRep = {
  id: 53247,
  obra: 'benficios transparent',
  subcontratista: 'Sub1',
  advance_status: 1,
};

export const sampleWithNewData: NewAdvPendRep = {
  obra: 'benficios transparent',
  subcontratista: 'Sub1',
  advance_status: 1,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
