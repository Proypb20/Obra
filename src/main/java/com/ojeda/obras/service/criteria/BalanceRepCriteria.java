package com.ojeda.obras.service.criteria;

import com.ojeda.obras.domain.enumeration.MetodoPago;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.BalanceRep} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.BalanceRepResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conceptos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BalanceRepCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    /**
     * Class for filtering MetodoPago
     */
    public static class MetodoPagoFilter extends Filter<MetodoPago> {

        public MetodoPagoFilter() {}

        public MetodoPagoFilter(BalanceRepCriteria.MetodoPagoFilter filter) {
            super(filter);
        }

        @Override
        public BalanceRepCriteria.MetodoPagoFilter copy() {
            return new BalanceRepCriteria.MetodoPagoFilter(this);
        }
    }

    private LongFilter id;

    private InstantFilter date;

    private BalanceRepCriteria.MetodoPagoFilter metodoPago;

    private DoubleFilter ingreso;

    private DoubleFilter egreso;

    private DoubleFilter amount;

    private Boolean distinct;

    public BalanceRepCriteria() {}

    public BalanceRepCriteria(BalanceRepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.metodoPago = other.metodoPago == null ? null : other.metodoPago.copy();
        this.ingreso = other.ingreso == null ? null : other.ingreso.copy();
        this.egreso = other.egreso == null ? null : other.egreso.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BalanceRepCriteria copy() {
        return new BalanceRepCriteria(this);
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

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public MetodoPagoFilter metodoPago() {
        if (metodoPago == null) {
            metodoPago = new MetodoPagoFilter();
        }
        return metodoPago;
    }

    public DoubleFilter ingreso() {
        if (ingreso == null) {
            ingreso = new DoubleFilter();
        }
        return ingreso;
    }

    public DoubleFilter egreso() {
        if (egreso == null) {
            egreso = new DoubleFilter();
        }
        return egreso;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public MetodoPagoFilter getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoFilter metodoPago) {
        this.metodoPago = metodoPago;
    }

    public DoubleFilter getIngreso() {
        return ingreso;
    }

    public void setIngreso(DoubleFilter ingreso) {
        this.ingreso = ingreso;
    }

    public DoubleFilter getEgreso() {
        return egreso;
    }

    public void setEgreso(DoubleFilter egreso) {
        this.egreso = egreso;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
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
        final BalanceRepCriteria that = (BalanceRepCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(metodoPago, that.metodoPago) &&
            Objects.equals(ingreso, that.ingreso) &&
            Objects.equals(egreso, that.egreso) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, metodoPago, ingreso, egreso, amount, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvObraRepCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (metodoPago != null ? "metodoPago=" + metodoPago + ", " : "") +
            (ingreso != null ? "ingreso=" + ingreso + ", " : "") +
            (egreso != null ? "egreso=" + egreso + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
