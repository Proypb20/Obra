select m1.id,m1.date,m1.metodo_pago,case when m1.amount > 0 then m1.amount else 0 end ingreso,case when m1.amount < 0 then ABS(m1.amount) else 0 end egreso,sum(m2.amount) amount
from
(select ROW_NUMBER() OVER () AS MID,m.id,m.date,m.metodo_pago,m.amount from movimiento m order by m.date,m.id) m1
,(select ROW_NUMBER() OVER () AS MID,m.id,m.date,m.metodo_pago,m.amount from movimiento m order by m.date,m.id) m2
where m1.mid >= m2.mid
group by m1.id,m1.date,m1.metodo_pago,m1.amount
