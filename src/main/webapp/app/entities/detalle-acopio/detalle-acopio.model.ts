import dayjs from 'dayjs/esm';
import { IAcopio } from 'app/entities/acopio/acopio.model';
import { Estado } from 'app/entities/enumerations/estado.model';

export interface IDetalleAcopio {
  id: number;
  date?: dayjs.Dayjs | null;
  description?: string | null;
  quantity?: number | null;
  unitPrice?: number | null;
  amount?: number | null;
  requestDate?: dayjs.Dayjs | null;
  promiseDate?: dayjs.Dayjs | null;
  deliveryStatus?: Estado | null;
  acopio?: Pick<IAcopio, 'id'> | null;
}

export type NewDetalleAcopio = Omit<IDetalleAcopio, 'id'> & { id: null };
