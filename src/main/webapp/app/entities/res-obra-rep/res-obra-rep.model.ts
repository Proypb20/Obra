import dayjs from 'dayjs/esm';

export interface IResObraRep {
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

export type NewResObraRep = Omit<IResObraRep, 'id'> & { id: null };
