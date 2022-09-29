import dayjs from 'dayjs/esm';
import { IProvincia } from 'app/entities/provincia/provincia.model';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';

export interface IObra {
  id: number;
  name?: string | null;
  address?: string | null;
  city?: string | null;
  comments?: string | null;
  provincia?: Pick<IProvincia, 'id' | 'name'> | null;
  subcontratistas?: Pick<ISubcontratista, 'id'>[] | null;
}

export type NewObra = Omit<IObra, 'id'> & { id: null };
