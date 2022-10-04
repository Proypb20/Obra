export interface ITipoComprobante {
  id: number;
  name?: string | null;
}

export type NewTipoComprobante = Omit<ITipoComprobante, 'id'> & { id: null };
