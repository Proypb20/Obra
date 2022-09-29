export interface IAdvPendRep {
  id: number;
  obraId?: number;
  obra?: string | null;
  subcontratistaId?: number;
  subcontratista?: string | null;
  advanceStatus?: number;
}

export type NewAdvPendRep = Omit<IAdvPendRep, 'id'> & { id: null };
