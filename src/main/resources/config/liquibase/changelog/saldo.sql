 SELECT METODO_PAGO
      , SUM(CASE WHEN TC.NAME = 'Pago' THEN AMOUNT * -1 ELSE AMOUNT END) AS SALDO
   FROM MOVIMIENTO M
   INNER JOIN TIPO_COMPROBANTE TC
   ON M.TIPO_COMPROBANTE_ID = TC.ID
    GROUP BY METODO_PAGO