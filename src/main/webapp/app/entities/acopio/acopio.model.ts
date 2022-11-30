import dayjs from 'dayjs/esm';
import { IObra } from 'app/entities/obra/obra.model';
import { IListaPrecio } from 'app/entities/lista-precio/lista-precio.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';

export interface IAcopio {
  id: number;
  date?: dayjs.Dayjs | null;
  totalAmount?: number | null;
  saldo?: number | null;
  obra?: Pick<IObra, 'id' | 'name'> | null;
  listaprecio?: Pick<IListaPrecio, 'id' | 'name'> | null;
  proveedor?: Pick<IProveedor, 'id' | 'name'> | null;
}

export type NewAcopio = Omit<IAcopio, 'id'> & { id: null };
