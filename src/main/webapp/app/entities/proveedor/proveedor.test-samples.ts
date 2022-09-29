import { IProveedor, NewProveedor } from './proveedor.model';

export const sampleWithRequiredData: IProveedor = {
  id: 27795,
  name: 'deposit AGP Intranet',
};

export const sampleWithPartialData: IProveedor = {
  id: 45796,
  name: 'Sabroso Account Funcionalidad',
  email: 'Mara62@hotmail.com',
};

export const sampleWithFullData: IProveedor = {
  id: 6079,
  name: 'deposit program',
  address: 'Cine',
  city: 'Manteca',
  phone: '905.897.109',
  email: 'JosLuis41@yahoo.com',
};

export const sampleWithNewData: NewProveedor = {
  name: 'Violeta bypassing',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
