export interface ITipoComprobante {
  id: number;
  name?: string | null;
  sign?: string | null;
}

export type NewTipoComprobante = Omit<ITipoComprobante, 'id'> & { id: null };
