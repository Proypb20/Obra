package com.ojeda.obras.service.criteria;

import com.ojeda.obras.domain.enumeration.Estado;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.DetalleAcopio} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.DetalleAcopioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /detalle-acopios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalleAcopioCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Estado
     */
    public static class EstadoFilter extends Filter<Estado> {

        public EstadoFilter() {}

        public EstadoFilter(EstadoFilter filter) {
            super(filter);
        }

        @Override
        public EstadoFilter copy() {
            return new EstadoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private StringFilter description;

    private LongFilter quantity;

    private LongFilter unitPrice;

    private LongFilter amount;

    private LocalDateFilter requestDate;

    private LocalDateFilter promiseDate;

    private EstadoFilter deliveryStatus;

    private LongFilter acopioId;

    private Boolean distinct;

    public DetalleAcopioCriteria() {}

    public DetalleAcopioCriteria(DetalleAcopioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.requestDate = other.requestDate == null ? null : other.requestDate.copy();
        this.promiseDate = other.promiseDate == null ? null : other.promiseDate.copy();
        this.deliveryStatus = other.deliveryStatus == null ? null : other.deliveryStatus.copy();
        this.acopioId = other.acopioId == null ? null : other.acopioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DetalleAcopioCriteria copy() {
        return new DetalleAcopioCriteria(this);
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

    public LongFilter getQuantity() {
        return quantity;
    }

    public LongFilter quantity() {
        if (quantity == null) {
            quantity = new LongFilter();
        }
        return quantity;
    }

    public void setQuantity(LongFilter quantity) {
        this.quantity = quantity;
    }

    public LongFilter getUnitPrice() {
        return unitPrice;
    }

    public LongFilter unitPrice() {
        if (unitPrice == null) {
            unitPrice = new LongFilter();
        }
        return unitPrice;
    }

    public void setUnitPrice(LongFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public LongFilter getAmount() {
        return amount;
    }

    public LongFilter amount() {
        if (amount == null) {
            amount = new LongFilter();
        }
        return amount;
    }

    public void setAmount(LongFilter amount) {
        this.amount = amount;
    }

    public LocalDateFilter getRequestDate() {
        return requestDate;
    }

    public LocalDateFilter requestDate() {
        if (requestDate == null) {
            requestDate = new LocalDateFilter();
        }
        return requestDate;
    }

    public void setRequestDate(LocalDateFilter requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateFilter getPromiseDate() {
        return promiseDate;
    }

    public LocalDateFilter promiseDate() {
        if (promiseDate == null) {
            promiseDate = new LocalDateFilter();
        }
        return promiseDate;
    }

    public void setPromiseDate(LocalDateFilter promiseDate) {
        this.promiseDate = promiseDate;
    }

    public EstadoFilter getDeliveryStatus() {
        return deliveryStatus;
    }

    public EstadoFilter deliveryStatus() {
        if (deliveryStatus == null) {
            deliveryStatus = new EstadoFilter();
        }
        return deliveryStatus;
    }

    public void setDeliveryStatus(EstadoFilter deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LongFilter getAcopioId() {
        return acopioId;
    }

    public LongFilter acopioId() {
        if (acopioId == null) {
            acopioId = new LongFilter();
        }
        return acopioId;
    }

    public void setAcopioId(LongFilter acopioId) {
        this.acopioId = acopioId;
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
        final DetalleAcopioCriteria that = (DetalleAcopioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(description, that.description) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(requestDate, that.requestDate) &&
            Objects.equals(promiseDate, that.promiseDate) &&
            Objects.equals(deliveryStatus, that.deliveryStatus) &&
            Objects.equals(acopioId, that.acopioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            date,
            description,
            quantity,
            unitPrice,
            amount,
            requestDate,
            promiseDate,
            deliveryStatus,
            acopioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleAcopioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (requestDate != null ? "requestDate=" + requestDate + ", " : "") +
            (promiseDate != null ? "promiseDate=" + promiseDate + ", " : "") +
            (deliveryStatus != null ? "deliveryStatus=" + deliveryStatus + ", " : "") +
            (acopioId != null ? "acopioId=" + acopioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
