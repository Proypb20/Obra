SELECT ROW_NUMBER() OVER () AS ID
      ,ID AS CONCEPTO_ID
	  ,NAME AS CONCEPTO_NAME
	  ,SIGN FROM (SELECT 1 ORDEN
	                    ,C.ID
                        ,C.NAME
                        ,'+' SIGN
                    FROM CONCEPTO C
                   WHERE C.ID IN (SELECT T.CONCEPTO_ID
				                    FROM TAREA T
                                GROUP BY T.CONCEPTO_ID)
                     AND UPPER(NAME) NOT IN ('ADICIONALES','DESCUENTOS')
                   UNION
                  SELECT 2 ORDEN
				        ,C.ID
                        ,C.NAME
                        ,CASE WHEN UPPER(C.NAME) = 'DESCUENTOS' THEN '-' ELSE '+' END SIGN
                    FROM CONCEPTO C
                   WHERE C.ID IN (SELECT T.CONCEPTO_ID
				                    FROM TAREA T
                                GROUP BY T.CONCEPTO_ID)
                     AND UPPER(NAME) IN ('ADICIONALES','DESCUENTOS')) SPC
 ORDER BY ORDEN
