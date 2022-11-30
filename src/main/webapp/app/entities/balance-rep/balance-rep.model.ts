import dayjs from 'dayjs/esm';
import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';

export interface IBalanceRep {
  id: number;
  date?: dayjs.Dayjs | null;
  metodoPago?: MetodoPago | null;
  ingreso?: number;
  egreso?: number;
  amount?: number;
}

export type NewBalanceRep = Omit<IBalanceRep, 'id'> & { id: null };
