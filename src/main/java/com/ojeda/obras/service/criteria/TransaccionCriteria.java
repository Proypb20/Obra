package com.ojeda.obras.service.criteria;

import com.ojeda.obras.domain.enumeration.MetodoPago;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.Transaccion} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.TransaccionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transaccions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransaccionCriteria implements Serializable, Criteria {

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

    private LocalDateFilter date;

    private MetodoPagoFilter paymentMethod;

    private StringFilter transactionNumber;

    private DoubleFilter amount;

    private StringFilter note;

    private LongFilter obraId;

    private LongFilter subcontratistaId;

    private LongFilter tipoComprobanteId;

    private LongFilter conceptoId;

    private Boolean distinct;

    public TransaccionCriteria() {}

    public TransaccionCriteria(TransaccionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.paymentMethod = other.paymentMethod == null ? null : other.paymentMethod.copy();
        this.transactionNumber = other.transactionNumber == null ? null : other.transactionNumber.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.obraId = other.obraId == null ? null : other.obraId.copy();
        this.subcontratistaId = other.subcontratistaId == null ? null : other.subcontratistaId.copy();
        this.tipoComprobanteId = other.tipoComprobanteId == null ? null : other.tipoComprobanteId.copy();
        this.conceptoId = other.conceptoId == null ? null : other.conceptoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransaccionCriteria copy() {
        return new TransaccionCriteria(this);
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

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public MetodoPagoFilter getPaymentMethod() {
        return paymentMethod;
    }

    public MetodoPagoFilter paymentMethod() {
        if (paymentMethod == null) {
            paymentMethod = new MetodoPagoFilter();
        }
        return paymentMethod;
    }

    public void setPaymentMethod(MetodoPagoFilter paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public StringFilter getNote() {
        return note;
    }

    public StringFilter note() {
        if (note == null) {
            note = new StringFilter();
        }
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
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
        final TransaccionCriteria that = (TransaccionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(paymentMethod, that.paymentMethod) &&
            Objects.equals(transactionNumber, that.transactionNumber) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(note, that.note) &&
            Objects.equals(obraId, that.obraId) &&
            Objects.equals(subcontratistaId, that.subcontratistaId) &&
            Objects.equals(tipoComprobanteId, that.tipoComprobanteId) &&
            Objects.equals(conceptoId, that.conceptoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            date,
            paymentMethod,
            transactionNumber,
            amount,
            note,
            obraId,
            subcontratistaId,
            tipoComprobanteId,
            conceptoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransaccionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (paymentMethod != null ? "paymentMethod=" + paymentMethod + ", " : "") +
            (transactionNumber != null ? "transactionNumber=" + transactionNumber + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (note != null ? "note=" + note + ", " : "") +
            (obraId != null ? "obraId=" + obraId + ", " : "") +
            (subcontratistaId != null ? "subcontratistaId=" + subcontratistaId + ", " : "") +
            (tipoComprobanteId != null ? "tipoComprobanteId=" + tipoComprobanteId + ", " : "") +
            (conceptoId != null ? "conceptoId=" + conceptoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
