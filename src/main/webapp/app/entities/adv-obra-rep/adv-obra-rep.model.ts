export interface IAdvObraRep {
  id: number;
  obra?: string | null;
  obraId?: number;
  concepto?: string | null;
  conceptoId?: number;
  taskName?: string | null;
  quantity?: number | null;
  cost?: number | null;
  advanceStatus?: number | null;
  total?: number | null;
  porcTarea?: number | null;
  porcAdv?: number | null;
}

export type NewAdvObraRep = Omit<IAdvObraRep, 'id'> & { id: null };
