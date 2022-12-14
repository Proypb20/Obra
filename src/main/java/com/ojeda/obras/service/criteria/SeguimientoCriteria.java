package com.ojeda.obras.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.AdvObraRep} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.AdvPendRepResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conceptos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SeguimientoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter obraName;

    private StringFilter periodName;

    private StringFilter conceptName;

    private DoubleFilter amount;

    private Boolean distinct;

    public SeguimientoCriteria() {}

    public SeguimientoCriteria(SeguimientoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.obraName = other.obraName == null ? null : other.obraName.copy();
        this.periodName = other.periodName == null ? null : other.periodName.copy();
        this.conceptName = other.conceptName == null ? null : other.conceptName.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SeguimientoCriteria copy() {
        return new SeguimientoCriteria(this);
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

    public StringFilter getObraName() {
        return obraName;
    }

    public StringFilter obraName() {
        if (obraName == null) {
            obraName = new StringFilter();
        }
        return obraName;
    }

    public StringFilter periodName() {
        if (periodName == null) {
            periodName = new StringFilter();
        }
        return periodName;
    }

    public StringFilter conceptName() {
        if (conceptName == null) {
            conceptName = new StringFilter();
        }
        return conceptName;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setObraName(StringFilter obraName) {
        this.obraName = obraName;
    }

    public StringFilter getPeriodName() {
        return periodName;
    }

    public void setPeriodName(StringFilter periodName) {
        this.periodName = periodName;
    }

    public StringFilter getConceptName() {
        return conceptName;
    }

    public void setConceptName(StringFilter conceptName) {
        this.conceptName = conceptName;
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
        final SeguimientoCriteria that = (SeguimientoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(obraName, that.obraName) &&
            Objects.equals(periodName, that.periodName) &&
            Objects.equals(conceptName, that.conceptName) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, obraName, periodName, conceptName, amount, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeguimientoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (obraName != null ? "obra=" + obraName + ", " : "") +
            (periodName != null ? "subcontratista=" + periodName + ", " : "") +
            (conceptName != null ? "subcontratista=" + conceptName + ", " : "") +
            (amount != null ? "advanceStatus=" + amount + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
