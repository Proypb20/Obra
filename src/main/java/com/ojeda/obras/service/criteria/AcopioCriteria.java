package com.ojeda.obras.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.Acopio} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.AcopioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /acopios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcopioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private DoubleFilter totalAmount;

    private LongFilter obraId;

    private LongFilter listaprecioId;

    private LongFilter proveedorId;

    private Boolean distinct;

    public AcopioCriteria() {}

    public AcopioCriteria(AcopioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.obraId = other.obraId == null ? null : other.obraId.copy();
        this.listaprecioId = other.listaprecioId == null ? null : other.listaprecioId.copy();
        this.proveedorId = other.proveedorId == null ? null : other.proveedorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AcopioCriteria copy() {
        return new AcopioCriteria(this);
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

    public DoubleFilter getTotalAmount() {
        return totalAmount;
    }

    public DoubleFilter totalAmount() {
        if (totalAmount == null) {
            totalAmount = new DoubleFilter();
        }
        return totalAmount;
    }

    public void setTotalAmount(DoubleFilter totalAmount) {
        this.totalAmount = totalAmount;
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

    public LongFilter getListaprecioId() {
        return listaprecioId;
    }

    public LongFilter listaprecioId() {
        if (listaprecioId == null) {
            listaprecioId = new LongFilter();
        }
        return listaprecioId;
    }

    public void setListaprecioId(LongFilter listaprecioId) {
        this.listaprecioId = listaprecioId;
    }

    public LongFilter getProveedorId() {
        return proveedorId;
    }

    public LongFilter proveedorId() {
        if (proveedorId == null) {
            proveedorId = new LongFilter();
        }
        return proveedorId;
    }

    public void setProveedorId(LongFilter proveedorId) {
        this.proveedorId = proveedorId;
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
        final AcopioCriteria that = (AcopioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(obraId, that.obraId) &&
            Objects.equals(listaprecioId, that.listaprecioId) &&
            Objects.equals(proveedorId, that.proveedorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, totalAmount, obraId, listaprecioId, proveedorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcopioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
            (obraId != null ? "obraId=" + obraId + ", " : "") +
            (listaprecioId != null ? "listaprecioId=" + listaprecioId + ", " : "") +
            (proveedorId != null ? "proveedorId=" + proveedorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
