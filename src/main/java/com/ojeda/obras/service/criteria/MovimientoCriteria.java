package com.ojeda.obras.service.criteria;

import com.ojeda.obras.domain.enumeration.MetodoPago;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.Movimiento} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.MovimientoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /movimientos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MovimientoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MetodoPago
     */
    public static class MetodoPagoFilter extends Filter<MetodoPago> {

        public MetodoPagoFilter() {}

        public MetodoPagoFilter(MetodoPagoFilter filter) {
            super(filter);
        }

        @Override
        public MetodoPagoFilter copy() {
            return new MetodoPagoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private StringFilter description;

    private MetodoPagoFilter metodoPago;

    private DoubleFilter amount;

    private StringFilter transactionNumber;

    private LongFilter obraId;

    private LongFilter subcontratistaId;

    private LongFilter conceptoId;

    private LongFilter tipoComprobanteId;

    private Boolean distinct;

    public MovimientoCriteria() {}

    public MovimientoCriteria(MovimientoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.metodoPago = other.metodoPago == null ? null : other.metodoPago.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.transactionNumber = other.transactionNumber == null ? null : other.transactionNumber.copy();
        this.obraId = other.obraId == null ? null : other.obraId.copy();
        this.subcontratistaId = other.subcontratistaId == null ? null : other.subcontratistaId.copy();
        this.conceptoId = other.conceptoId == null ? null : other.conceptoId.copy();
        this.tipoComprobanteId = other.tipoComprobanteId == null ? null : other.tipoComprobanteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MovimientoCriteria copy() {
        return new MovimientoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public MetodoPagoFilter getMetodoPago() {
        return metodoPago;
    }

    public MetodoPagoFilter metodoPago() {
        if (metodoPago == null) {
            metodoPago = new MetodoPagoFilter();
        }
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoFilter metodoPago) {
        this.metodoPago = metodoPago;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public StringFilter getTransactionNumber() {
        return transactionNumber;
    }

    public StringFilter transactionNumber() {
        if (transactionNumber == null) {
            transactionNumber = new StringFilter();
        }
        return transactionNumber;
    }

    public void setTransactionNumber(StringFilter transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public LongFilter getObraId() {
        return obraId;
    }

    public LongFilter obraId() {
        if (obraId == null) {
            obraId = new LongFilter();
        }
        return obraId;
    }

    public void setObraId(LongFilter obraId) {
        this.obraId = obraId;
    }

    public LongFilter getSubcontratistaId() {
        return subcontratistaId;
    }

    public LongFilter subcontratistaId() {
        if (subcontratistaId == null) {
            subcontratistaId = new LongFilter();
        }
        return subcontratistaId;
    }

    public void setSubcontratistaId(LongFilter subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
    }

    public LongFilter getConceptoId() {
        return conceptoId;
    }

    public LongFilter conceptoId() {
        if (conceptoId == null) {
            conceptoId = new LongFilter();
        }
        return conceptoId;
    }

    public void setConceptoId(LongFilter conceptoId) {
        this.conceptoId = conceptoId;
    }

    public LongFilter getTipoComprobanteId() {
        return tipoComprobanteId;
    }

    public LongFilter tipoComprobanteId() {
        if (tipoComprobanteId == null) {
            tipoComprobanteId = new LongFilter();
        }
        return tipoComprobanteId;
    }

    public void setTipoComprobanteId(LongFilter tipoComprobanteId) {
        this.tipoComprobanteId = tipoComprobanteId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MovimientoCriteria that = (MovimientoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(description, that.description) &&
            Objects.equals(metodoPago, that.metodoPago) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(transactionNumber, that.transactionNumber) &&
            Objects.equals(obraId, that.obraId) &&
            Objects.equals(subcontratistaId, that.subcontratistaId) &&
            Objects.equals(conceptoId, that.conceptoId) &&
            Objects.equals(tipoComprobanteId, that.tipoComprobanteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            date,
            description,
            metodoPago,
            amount,
            transactionNumber,
            obraId,
            subcontratistaId,
            conceptoId,
            tipoComprobanteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovimientoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (metodoPago != null ? "metodoPago=" + metodoPago + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (transactionNumber != null ? "transactionNumber=" + transactionNumber + ", " : "") +
            (obraId != null ? "obraId=" + obraId + ", " : "") +
            (subcontratistaId != null ? "subcontratistaId=" + subcontratistaId + ", " : "") +
            (conceptoId != null ? "conceptoId=" + conceptoId + ", " : "") +
            (tipoComprobanteId != null ? "tipoComprobanteId=" + tipoComprobanteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
