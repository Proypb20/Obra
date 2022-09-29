import dayjs from 'dayjs/esm';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';

export interface IListaPrecio {
  id: number;
  name?: string | null;
  date?: dayjs.Dayjs | null;
  proveedor?: Pick<IProveedor, 'id' | 'name'> | null;
}

export type NewListaPrecio = Omit<IListaPrecio, 'id'> & { id: null };
