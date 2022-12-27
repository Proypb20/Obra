 SELECT T.ID
      , O.NAME AS OBRA
	  , T.OBRA_ID
	  , S.ID AS SUBCONTRATISTA_ID
	  , S.LAST_NAME || ', ' || S.FIRST_NAME AS SUBCONTRATISTA
	  , C.NAME AS CONCEPTO
	  , T.CONCEPTO_ID
	  , T.NAME AS TASK_NAME
	  , T.QUANTITY
	  , T.COST
	  , T.ADVANCE_STATUS
	  , ROUND(SUM(COST) OVER (PARTITION BY T.OBRA_ID), 2) AS TOTAL_OBRA
	  , ROUND(SUM(COST) OVER (PARTITION BY T.OBRA_ID,T.SUBCONTRATISTA_ID), 2) AS TOTAL_SUBCO
	  , ROUND((T.COST / SUM(COST) OVER (PARTITION BY T.OBRA_ID)) * 100, 2) AS PORC_TAREA
	  , ROUND((T.COST / SUM(COST) OVER (PARTITION BY T.OBRA_ID,T.SUBCONTRATISTA_ID)) * 100, 2) AS PORC_TAREA_SUBCO
	  , ROUND(SUM(T2.PORC_ADV1), 2) AS PORC_ADV
	  , ROUND(SUM(T2.PORC_ADV_SUBCO1), 2) AS PORC_ADV_SUBCO
	  FROM TAREA T
	  INNER JOIN ( SELECT T1.ID
	                    , T1.OBRA_ID
,t1.subcontratista_id
						, ROUND((T1.COST / SUM(T1.COST) OVER (PARTITION BY T1.OBRA_ID)) * T1.ADVANCE_STATUS, 2) AS PORC_ADV1
						, ROUND((T1.COST / SUM(T1.COST) OVER (PARTITION BY T1.OBRA_ID,T1.SUBCONTRATISTA_ID)) * T1.ADVANCE_STATUS, 2) AS PORC_ADV_SUBCO1
						FROM TAREA T1 ) T2 ON 1=1
	  INNER JOIN OBRA O ON 1=1
	  INNER JOIN CONCEPTO C ON 1=1
	  INNER JOIN SUBCONTRATISTA S ON 1=1
	  WHERE (T.OBRA_ID = O.ID)
	  AND (T.CONCEPTO_ID = C.ID)
	  AND (T.OBRA_ID = T2.OBRA_ID)
	  AND (T.SUBCONTRATISTA_ID = T2.SUBCONTRATISTA_ID)
	  AND (T.SUBCONTRATISTA_ID = S.ID)
	  AND (T.ID >= T2.ID)
	  GROUP BY O.NAME
	  , T.OBRA_ID
	  , C.NAME
	  , T.CONCEPTO_ID
	  , T.NAME
	  , T.QUANTITY
	  , T.COST
	  , T.ADVANCE_STATUS
	  , S.ID
	  , S.LAST_NAME || ', ' || S.FIRST_NAME
	  ORDER BY 1
