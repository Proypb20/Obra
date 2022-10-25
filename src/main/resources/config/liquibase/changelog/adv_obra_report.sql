select t.id
, o.name as obra
,t.obra_id
,c.name concepto
,t.concepto_id
,t.name task_name
,t.quantity
,t.cost
,t.advance_status
,ROUND(sum(cost) over(partition by t.obra_id),2) total
,round((t.cost/sum(cost) over(partition by t.obra_id))*100,2) porc_tarea
,round(sum(t2.porc_adv1),2) porc_adv
from tarea t
,(select t1.id,t1.obra_id,round((t1.cost/sum(t1.cost) over(partition by t1.obra_id)) * t1.advance_status  ,2) porc_adv1 from tarea t1) t2
,obra o
,concepto c
where t.obra_id = o.id
and t.concepto_id = c.id
and t.obra_id = t2.obra_id
and t.id >= t2.id
group by o.name
,t.obra_id
,c.name
,t.concepto_id
,t.name
,t.quantity
,t.cost
,t.advance_status
order by t.id;
