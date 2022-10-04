import { IProvincia } from 'app/entities/provincia/provincia.model';
import { IObra } from 'app/entities/obra/obra.model';

export interface ICliente {
  id: number;
  lastName?: string | null;
  firstName?: string | null;
  taxpayerID?: string | null;
  address?: string | null;
  city?: string | null;
  provincia?: Pick<IProvincia, 'id' | 'name'> | null;
  obras?: Pick<IObra, 'id' | 'name'>[] | null;
}

export type NewCliente = Omit<ICliente, 'id'> & { id: null };
