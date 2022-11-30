export interface IAdvObraRep {
  id: number;
  obra?: string | null;
  obraId?: number;
  subcontratista?: string | null;
  subcontratistaId?: number;
  concepto?: string | null;
  conceptoId?: number;
  taskName?: string | null;
  quantity?: number | null;
  cost?: number | null;
  advanceStatus?: number | null;
  total?: number | null;
  totalSubco?: number | null;
  porcTarea?: number | null;
  porcTareaSubco?: number | null;
  porcAdv?: number | null;
  porcAdvSubco?: number | null;
}

export type NewAdvObraRep = Omit<IAdvObraRep, 'id'> & { id: null };
