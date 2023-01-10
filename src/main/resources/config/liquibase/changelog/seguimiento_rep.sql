 SELECT ROW_NUMBER() OVER () AS ID
      ,o.name obra_name
      ,TO_CHAR("M"."DATE", 'Mon-yy') period_name
      ,c.name concept_name
      ,SUM(m.amount * -1) amount
 FROM MOVIMIENTO m
 JOIN tipo_comprobante tc on tc.id = m.tipo_comprobante_id
 JOIN concepto c on c.id = m.concepto_id
 JOIN obra o on o.id = m.obra_id
WHERE tc.name = 'Pago'
group by TO_CHAR("M"."DATE", 'Mon-yy')
,o.name
,c.name
