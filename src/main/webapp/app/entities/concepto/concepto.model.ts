export interface IConcepto {
  id: number;
  name?: string | null;
}

export type NewConcepto = Omit<IConcepto, 'id'> & { id: null };
