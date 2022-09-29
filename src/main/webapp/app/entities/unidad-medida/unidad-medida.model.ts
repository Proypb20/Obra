export interface IUnidadMedida {
  id: number;
  name?: string | null;
}

export type NewUnidadMedida = Omit<IUnidadMedida, 'id'> & { id: null };
