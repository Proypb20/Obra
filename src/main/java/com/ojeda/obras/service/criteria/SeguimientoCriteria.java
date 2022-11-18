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

    private LocalDateFilter date;

    private StringFilter periodName;

    private StringFilter source;

    private StringFilter reference;

    private StringFilter description;

    private StringFilter type;

    private DoubleFilter amount;

    private Boolean distinct;

    public SeguimientoCriteria() {}

    public SeguimientoCriteria(SeguimientoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.obraName = other.obraName == null ? null : other.obraName.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.periodName = other.periodName == null ? null : other.periodName.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
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

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public StringFilter periodName() {
        if (periodName == null) {
            periodName = new StringFilter();
        }
        return periodName;
    }

    public StringFilter source() {
        if (source == null) {
            source = new StringFilter();
        }
        return source;
    }

    public StringFilter reference() {
        if (reference == null) {
            reference = new StringFilter();
        }
        return reference;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
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

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public StringFilter getPeriodName() {
        return periodName;
    }

    public void setPeriodName(StringFilter periodName) {
        this.periodName = periodName;
    }

    public StringFilter getSource() {
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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
            Objects.equals(date, that.date) &&
            Objects.equals(periodName, that.periodName) &&
            Objects.equals(source, that.source) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, obraName, date, periodName, source, reference, description, type, amount, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeguimientoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (obraName != null ? "obra=" + obraName + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (periodName != null ? "subcontratista=" + periodName + ", " : "") +
            (source != null ? "subcontratista=" + source + ", " : "") +
            (reference != null ? "subcontratista=" + reference + ", " : "") +
            (description != null ? "subcontratista=" + description + ", " : "") +
            (type != null ? "subcontratista=" + type + ", " : "") +
            (amount != null ? "advanceStatus=" + amount + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
