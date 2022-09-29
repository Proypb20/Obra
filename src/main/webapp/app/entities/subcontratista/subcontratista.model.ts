import { IObra } from 'app/entities/obra/obra.model';

export interface ISubcontratista {
  id: number;
  lastName?: string | null;
  firstName?: string | null;
  phone?: string | null;
  email?: string | null;
  obras?: Pick<IObra, 'id' | 'name'>[] | null;
}

export type NewSubcontratista = Omit<ISubcontratista, 'id'> & { id: null };
