import { IListaPrecio } from 'app/entities/lista-precio/lista-precio.model';

export interface IDetalleListaPrecio {
  id: number;
  code?: string | null;
  product?: string | null;
  amount?: number | null;
  listaPrecio?: Pick<IListaPrecio, 'id' | 'name'> | null;
}

export type NewDetalleListaPrecio = Omit<IDetalleListaPrecio, 'id'> & { id: null };
