import { ICliente, NewCliente } from './cliente.model';

export const sampleWithRequiredData: ICliente = {
  id: 45820,
};

export const sampleWithPartialData: ICliente = {
  id: 2527,
  firstName: 'Víctor',
  taxpayerID: 'Berkshire Genérico recontextualize',
  address: 'HDD',
};

export const sampleWithFullData: ICliente = {
  id: 94940,
  lastName: 'Robles',
  firstName: 'Eva',
  taxpayerID: 'Bicicleta enterprise Guam',
  address: 'de JBOD invoice',
  city: 'Pazfurt',
};

export const sampleWithNewData: NewCliente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
