import { ISubcontratista, NewSubcontratista } from './subcontratista.model';

export const sampleWithRequiredData: ISubcontratista = {
  id: 57575,
  lastName: 'Coronado',
  firstName: 'Andrea',
};

export const sampleWithPartialData: ISubcontratista = {
  id: 90480,
  lastName: 'Castro',
  firstName: 'Yolanda',
  phone: '979 465 099',
};

export const sampleWithFullData: ISubcontratista = {
  id: 23922,
  lastName: 'Soto',
  firstName: 'Guillermina',
  phone: '980-240-114',
  email: 'Ramona_Santiago@gmail.com',
};

export const sampleWithNewData: NewSubcontratista = {
  lastName: 'Batista',
  firstName: 'Bernardo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
