package com.ojeda.obras.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.Tarea} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.TareaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tareas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TareaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private DoubleFilter quantity;

    private DoubleFilter cost;

    private DoubleFilter advanceStatus;

    private LongFilter obraId;

    private LongFilter subcontratistaId;

    private LongFilter conceptoId;

    private Boolean distinct;

    public TareaCriteria() {}

    public TareaCriteria(TareaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.cost = other.cost == null ? null : other.cost.copy();
        this.advanceStatus = other.advanceStatus == null ? null : other.advanceStatus.copy();
        this.obraId = other.obraId == null ? null : other.obraId.copy();
        this.subcontratistaId = other.subcontratistaId == null ? null : other.subcontratistaId.copy();
        this.conceptoId = other.conceptoId == null ? null : other.conceptoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TareaCriteria copy() {
        return new TareaCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public DoubleFilter quantity() {
        if (quantity == null) {
            quantity = new DoubleFilter();
        }
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public DoubleFilter getCost() {
        return cost;
    }

    public DoubleFilter cost() {
        if (cost == null) {
            cost = new DoubleFilter();
        }
        return cost;
    }

    public void setCost(DoubleFilter cost) {
        this.cost = cost;
    }

    public DoubleFilter getAdvanceStatus() {
        return advanceStatus;
    }

    public DoubleFilter advanceStatus() {
        if (advanceStatus == null) {
            advanceStatus = new DoubleFilter();
        }
        return advanceStatus;
    }

    public void setAdvanceStatus(DoubleFilter advanceStatus) {
        this.advanceStatus = advanceStatus;
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
        final TareaCriteria that = (TareaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(advanceStatus, that.advanceStatus) &&
            Objects.equals(obraId, that.obraId) &&
            Objects.equals(subcontratistaId, that.subcontratistaId) &&
            Objects.equals(conceptoId, that.conceptoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, cost, advanceStatus, obraId, subcontratistaId, conceptoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TareaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (cost != null ? "cost=" + cost + ", " : "") +
            (advanceStatus != null ? "advanceStatus=" + advanceStatus + ", " : "") +
            (obraId != null ? "obraId=" + obraId + ", " : "") +
            (subcontratistaId != null ? "subcontratistaId=" + subcontratistaId + ", " : "") +
            (conceptoId != null ? "conceptoId=" + conceptoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
