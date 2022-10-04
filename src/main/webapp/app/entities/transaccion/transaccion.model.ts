import dayjs from 'dayjs/esm';
import { IObra } from 'app/entities/obra/obra.model';
import { ISubcontratista } from 'app/entities/subcontratista/subcontratista.model';
import { ITipoComprobante } from 'app/entities/tipo-comprobante/tipo-comprobante.model';
import { MetodoPago } from 'app/entities/enumerations/metodo-pago.model';
import { IConcepto } from 'app/entities/concepto/concepto.model';

export interface ITransaccion {
  id: number;
  date?: dayjs.Dayjs | null;
  paymentMethod?: MetodoPago | null;
  transactionNumber?: string | null;
  amount?: number | null;
  note?: string | null;
  obra?: Pick<IObra, 'id' | 'name'> | null;
  subcontratista?: Pick<ISubcontratista, 'id' | 'lastName' | 'firstName'> | null;
  tipoComprobante?: Pick<ITipoComprobante, 'id' | 'name'> | null;
  concepto?: Pick<IConcepto, 'id' | 'name'> | null;
}

export type NewTransaccion = Omit<ITransaccion, 'id'> & { id: null };
