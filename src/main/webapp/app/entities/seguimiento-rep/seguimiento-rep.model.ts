import dayjs from 'dayjs/esm';

export interface ISeguimientoRep {
  id: number;
  obraName?: string | null;
  date?: dayjs.Dayjs | null;
  periodName?: string | null;
  source?: string | null;
  reference?: string | null;
  description?: string | null;
  type?: string | null;
  amount?: number | null;
}

export type NewSeguimientoRep = Omit<ISeguimientoRep, 'id'> & { id: null };
