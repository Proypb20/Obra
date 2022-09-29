export interface IProvincia {
  id: number;
  name?: string | null;
}

export type NewProvincia = Omit<IProvincia, 'id'> & { id: null };
