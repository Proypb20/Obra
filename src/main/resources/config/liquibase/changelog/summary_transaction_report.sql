SELECT ROW_NUMBER() OVER (ORDER BY O.NAME) AS ID
     , O.ID AS OBRA_ID
     , O.NAME AS OBRA
     , DATE AS FECHA
     , S.LAST_NAME || ', ' || S.FIRST_NAME AS SUBCONTRATISTA
     , TC.NAME AS TIPO_COMPROBANTE
     , T.TRANSACTION_NUMBER
     , C.NAME AS CONCEPTO
     , NULL AS DEBIT_AMOUNT
     , T.AMOUNT AS CREDIT_AMOUNT
  FROM TRANSACCION T
  INNER JOIN OBRA O ON T.OBRA_ID = O.ID
  INNER JOIN SUBCONTRATISTA S ON T.SUBCONTRATISTA_ID = S.ID
  INNER JOIN TIPO_COMPROBANTE TC ON T.TIPO_COMPROBANTE_ID = TC.ID
  INNER JOIN CONCEPTO C ON T.CONCEPTO_ID = C.ID;
