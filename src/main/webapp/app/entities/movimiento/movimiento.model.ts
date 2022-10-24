import dayjs from 'dayjs/esm';
import { IObra } from 'app/entities/obra/obra.model';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { IConcepto } from 'app/entities/concepto/concepto.model';
import { ITipoComprobante } from 'app/entities/tipo-comprobante/tipo-comprobante.model';
import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';

export interface IMovimiento {
  id: number;
  date?: dayjs.Dayjs | null;
  description?: string | null;
  metodoPago?: MetodoPago | null;
  amount?: number | null;
  transactionNumber?: string | null;
  obra?: Pick<IObra, 'id' | 'name'> | null;
  subcontratista?: Pick<ISubcontratista, 'id' | 'lastName' | 'firstName'> | null;
  concepto?: Pick<IConcepto, 'id' | 'name'> | null;
  tipoComprobante?: Pick<ITipoComprobante, 'id' | 'name'> | null;
}

export type NewMovimiento = Omit<IMovimiento, 'id'> & { id: null };
