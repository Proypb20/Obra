import { IProvincia } from 'app/entities/provincia/provincia.model';

export interface IProveedor {
  id: number;
  name?: string | null;
  address?: string | null;
  city?: string | null;
  phone?: string | null;
  email?: string | null;
  provincia?: Pick<IProvincia, 'id' | 'name'> | null;
}

export type NewProveedor = Omit<IProveedor, 'id'> & { id: null };
