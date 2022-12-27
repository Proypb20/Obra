SELECT o.id AS obra_id
	,o.name AS obra_name
	,c.id AS concepto_id
	,c.name concepto
	,coalesce((
			SELECT SUM(t.cost)
			FROM tarea t
			WHERE t.concepto_id = c.id
				AND t.obra_id = o.id
			), 0) - coalesce((
			SELECT sum(amount) * - 1
			FROM MOVIMIENTO
			WHERE amount < 0
				AND (
					obra_id IS NOT NULL
					OR subcontratista_id IS NOT NULL
					)
				AND concepto_id = c.id
				AND obra_id = o.id
			), 0) saldo
	,(
		SELECT coalesce(SUM(coalesce(t.cost, 0)), 0)
		FROM tarea t
		WHERE t.concepto_id = c.id
			AND t.obra_id = o.id
		) presupuesto
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
		) pagos
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '01'
		) enero
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '02'
		) febrero
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '03'
		) marzo
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '04'
		) abril
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '05'
		) mayo
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '06'
		) junio
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '07'
		) julio
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '08'
		) agosto
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '09'
		) septiembre
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '10'
		) octubre
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '11'
		) noviembre
	,(
		SELECT coalesce(sum(coalesce(amount, 0)), 0) * - 1
		FROM MOVIMIENTO
		WHERE amount < 0
			AND (
				obra_id IS NOT NULL
				OR subcontratista_id IS NOT NULL
				)
			AND concepto_id = c.id
			AND obra_id = o.id
			AND to_char(DATE, 'MM') = '12'
		) diciembre
FROM concepto c
	,obra o
ORDER BY o.id
	,c.id
