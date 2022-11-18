export interface ISaldo {
  metodoPago: string;
  saldo?: number | null;
}

export type NewSaldo = Omit<ISaldo, 'id'> & { id: null };
