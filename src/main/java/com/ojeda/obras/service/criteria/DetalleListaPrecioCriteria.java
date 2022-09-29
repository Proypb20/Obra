package com.ojeda.obras.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.DetalleListaPrecio} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.DetalleListaPrecioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /detalle-lista-precios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalleListaPrecioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter product;

    private FloatFilter amount;

    private LongFilter listaPrecioId;

    private Boolean distinct;

    public DetalleListaPrecioCriteria() {}

    public DetalleListaPrecioCriteria(DetalleListaPrecioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.product = other.product == null ? null : other.product.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.listaPrecioId = other.listaPrecioId == null ? null : other.listaPrecioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DetalleListaPrecioCriteria copy() {
        return new DetalleListaPrecioCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getProduct() {
        return product;
    }

    public StringFilter product() {
        if (product == null) {
            product = new StringFilter();
        }
        return product;
    }

    public void setProduct(StringFilter product) {
        this.product = product;
    }

    public FloatFilter getAmount() {
        return amount;
    }

    public FloatFilter amount() {
        if (amount == null) {
            amount = new FloatFilter();
        }
        return amount;
    }

    public void setAmount(FloatFilter amount) {
        this.amount = amount;
    }

    public LongFilter getListaPrecioId() {
        return listaPrecioId;
    }

    public LongFilter listaPrecioId() {
        if (listaPrecioId == null) {
            listaPrecioId = new LongFilter();
        }
        return listaPrecioId;
    }

    public void setListaPrecioId(LongFilter listaPrecioId) {
        this.listaPrecioId = listaPrecioId;
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
        final DetalleListaPrecioCriteria that = (DetalleListaPrecioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(product, that.product) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(listaPrecioId, that.listaPrecioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, product, amount, listaPrecioId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleListaPrecioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (product != null ? "product=" + product + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (listaPrecioId != null ? "listaPrecioId=" + listaPrecioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
