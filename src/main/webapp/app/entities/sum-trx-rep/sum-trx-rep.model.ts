import dayjs from 'dayjs/esm';

export interface ISumTrxRep {
  id: number;
  obraId?: number;
  obra?: string | null;
  fecha?: dayjs.Dayjs | null;
  subcontratista?: string | null;
  tipoComprobante?: string | null;
  transactionNumber?: string | null;
  concepto?: string | null;
  debitAmount?: number | null;
  creditAmount?: number | null;
}

export type NewSumTrxRep = Omit<ISumTrxRep, 'id'> & { id: null };
