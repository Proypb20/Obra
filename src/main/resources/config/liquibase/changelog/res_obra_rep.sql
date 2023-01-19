select m.id
,o.name as obra_name
,m.date
,to_char(m.date,'Mon-yy') as period_name
,case when m.amount > 0 then o.name else c.name end source
,tc.name||' '||m.transaction_number as reference
,m.description
,case when m.amount > 0 then 'I' else 'E' end type
,abs(amount) as amount
from movimiento m
inner join obra o on m.obra_id = o.id
left join concepto c on m.concepto_id = c.id
left join tipo_comprobante tc on m.tipo_comprobante_id = tc.id
