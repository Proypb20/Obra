SELECT ROW_NUMBER() OVER (ORDER BY O.NAME) AS ID
       , O.ID AS OBRA_ID
       ,O.NAME AS OBRA
       , S.ID AS SUBCONTRATISTA_ID
       ,CONCAT(S.LAST_NAME , ', ' , S.FIRST_NAME) AS SUBCONTRATISTA
       , ROUND(SUM(T.ADVANCE_STATUS) / COUNT(*), 5) AS ADVANCE_STATUS FROM TAREA T
       INNER JOIN OBRA O ON 1=1 INNER JOIN SUBCONTRATISTA S ON 1=1
       WHERE (T.OBRA_ID = O.ID) AND (T.SUBCONTRATISTA_ID = S.ID)
       GROUP BY  O.ID ,O.NAME,S.ID, CONCAT(S.LAST_NAME , ', ' , S.FIRST_NAME)
       HAVING (SUM(T.ADVANCE_STATUS) / COUNT(*)) < 100;
