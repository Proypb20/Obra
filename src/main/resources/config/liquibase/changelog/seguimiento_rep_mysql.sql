 SELECT ROW_NUMBER() OVER () AS ID
      ,o.name obra_name
      , date_format(m.date, '%b-%y') as period_name
      ,m.date
      ,c.name concept_name
      ,SUM(m.amount * -1) amount
 FROM MOVIMIENTO m
 JOIN tipo_comprobante tc on tc.id = m.tipo_comprobante_id
 JOIN concepto c on c.id = m.concepto_id
 JOIN obra o on o.id = m.obra_id
WHERE tc.name = 'Pago'
group by date_format(m.date, '%b-%y')
,o.name
,c.name
