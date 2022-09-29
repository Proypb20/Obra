import { IUnidadMedida } from 'app/entities/unidad-medida/unidad-medida.model';
import { IObra } from 'app/entities/obra/obra.model';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { IConcepto } from 'app/entities/concepto/concepto.model';

export interface ITarea {
  id: number;
  name?: string | null;
  quantity?: number | null;
  cost?: number | null;
  advanceStatus?: number | null;
  obra?: Pick<IObra, 'id' | 'name'> | null;
  subcontratista?: Pick<ISubcontratista, 'id' | 'lastName' | 'firstName'> | null;
  concepto?: Pick<IConcepto, 'id' | 'name'> | null;
}

export type NewTarea = Omit<ITarea, 'id'> & { id: null };
