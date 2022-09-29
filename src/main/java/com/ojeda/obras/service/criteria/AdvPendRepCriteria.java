package com.ojeda.obras.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.AdvPendRep} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.AdvPendRepResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conceptos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdvPendRepCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter obraId;

    private StringFilter obra;

    private LongFilter subcontratistaId;

    private StringFilter subcontratista;

    private LongFilter advanceStatus;

    private Boolean distinct;

    public AdvPendRepCriteria() {}

    public AdvPendRepCriteria(AdvPendRepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.obraId = other.obraId == null ? null : other.obraId.copy();
        this.obra = other.obra == null ? null : other.obra.copy();
        this.subcontratistaId = other.subcontratistaId == null ? null : other.subcontratistaId.copy();
        this.subcontratista = other.subcontratista == null ? null : other.subcontratista.copy();
        this.advanceStatus = other.advanceStatus == null ? null : other.advanceStatus.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AdvPendRepCriteria copy() {
        return new AdvPendRepCriteria(this);
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

    public LongFilter getObraId() {
        return obraId;
    }

    public StringFilter getObra() {
        return obra;
    }

    public LongFilter getSubcontratistaId() {
        return subcontratistaId;
    }

    public StringFilter getSubcontratista() {
        return subcontratista;
    }

    public LongFilter obraId() {
        if (obraId == null) {
            obraId = new LongFilter();
        }
        return obraId;
    }

    public StringFilter obra() {
        if (obra == null) {
            obra = new StringFilter();
        }
        return obra;
    }

    public LongFilter subcontratistaId() {
        if (subcontratistaId == null) {
            subcontratistaId = new LongFilter();
        }
        return subcontratistaId;
    }

    public StringFilter subcontratista() {
        if (subcontratista == null) {
            subcontratista = new StringFilter();
        }
        return subcontratista;
    }

    public LongFilter advanceStatus() {
        if (advanceStatus == null) {
            advanceStatus = new LongFilter();
        }
        return advanceStatus;
    }

    public void setObraId(LongFilter obraId) {
        this.obraId = obraId;
    }

    public void setObra(StringFilter obra) {
        this.obra = obra;
    }

    public void setSubcontratistaId(LongFilter subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
    }

    public void setSubcontratista(StringFilter subcontratista) {
        this.subcontratista = subcontratista;
    }

    public void setadvanceStatus(LongFilter advanceStatus) {
        this.advanceStatus = advanceStatus;
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
        final AdvPendRepCriteria that = (AdvPendRepCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(obraId, that.obraId) &&
            Objects.equals(obra, that.obra) &&
            Objects.equals(subcontratistaId, that.subcontratistaId) &&
            Objects.equals(subcontratista, that.subcontratista) &&
            Objects.equals(advanceStatus, that.advanceStatus) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, obraId, obra, subcontratistaId, subcontratista, advanceStatus, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvPendRepCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (obraId != null ? "obraId=" + obraId + ", " : "") +
            (obra != null ? "obra=" + obra + ", " : "") +
            (subcontratistaId != null ? "subcontratistaId=" + subcontratistaId + ", " : "") +
            (subcontratista != null ? "subcontratista=" + subcontratista + ", " : "") +
            (advanceStatus != null ? "advanceStatus=" + advanceStatus + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
