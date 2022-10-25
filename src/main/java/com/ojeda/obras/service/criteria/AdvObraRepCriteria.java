package com.ojeda.obras.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

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
public class AdvObraRepCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter obra;

    private LongFilter obraId;

    private StringFilter concepto;

    private LongFilter conceptoId;

    private StringFilter taskName;

    private FloatFilter quantity;

    private FloatFilter cost;

    private FloatFilter advanceStatus;

    private FloatFilter total;

    private FloatFilter porcTarea;

    private FloatFilter porcAdv;

    private Boolean distinct;

    public AdvObraRepCriteria() {}

    public AdvObraRepCriteria(AdvObraRepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.obra = other.obra == null ? null : other.obra.copy();
        this.obraId = other.obraId == null ? null : other.obraId.copy();
        this.concepto = other.concepto == null ? null : other.concepto.copy();
        this.conceptoId = other.conceptoId == null ? null : other.conceptoId.copy();
        this.taskName = other.taskName == null ? null : other.taskName.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.cost = other.cost == null ? null : other.cost.copy();
        this.advanceStatus = other.advanceStatus == null ? null : other.advanceStatus.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.porcTarea = other.porcTarea == null ? null : other.porcTarea.copy();
        this.porcAdv = other.porcAdv == null ? null : other.porcAdv.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AdvObraRepCriteria copy() {
        return new AdvObraRepCriteria(this);
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

    public StringFilter concepto() {
        if (concepto == null) {
            concepto = new StringFilter();
        }
        return concepto;
    }

    public StringFilter taskName() {
        if (taskName == null) {
            taskName = new StringFilter();
        }
        return taskName;
    }

    public LongFilter conceptoId() {
        if (conceptoId == null) {
            conceptoId = new LongFilter();
        }
        return conceptoId;
    }

    public FloatFilter advanceStatus() {
        if (advanceStatus == null) {
            advanceStatus = new FloatFilter();
        }
        return advanceStatus;
    }

    public FloatFilter quantity() {
        if (quantity == null) {
            quantity = new FloatFilter();
        }
        return quantity;
    }

    public FloatFilter cost() {
        if (cost == null) {
            cost = new FloatFilter();
        }
        return cost;
    }

    public FloatFilter total() {
        if (total == null) {
            total = new FloatFilter();
        }
        return total;
    }

    public FloatFilter porcTarea() {
        if (porcTarea == null) {
            porcTarea = new FloatFilter();
        }
        return porcTarea;
    }

    public FloatFilter porcAdv() {
        if (porcAdv == null) {
            porcAdv = new FloatFilter();
        }
        return porcAdv;
    }

    public void setObraId(LongFilter obraId) {
        this.obraId = obraId;
    }

    public void setObra(StringFilter obra) {
        this.obra = obra;
    }

    public void setadvanceStatus(FloatFilter advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public StringFilter getConcepto() {
        return concepto;
    }

    public void setConcepto(StringFilter concepto) {
        this.concepto = concepto;
    }

    public LongFilter getConceptoId() {
        return conceptoId;
    }

    public void setConceptoId(LongFilter conceptoId) {
        this.conceptoId = conceptoId;
    }

    public StringFilter getTaskName() {
        return taskName;
    }

    public void setTaskName(StringFilter taskName) {
        this.taskName = taskName;
    }

    public FloatFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(FloatFilter quantity) {
        this.quantity = quantity;
    }

    public FloatFilter getCost() {
        return cost;
    }

    public void setCost(FloatFilter cost) {
        this.cost = cost;
    }

    public FloatFilter getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(FloatFilter advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public FloatFilter getTotal() {
        return total;
    }

    public void setTotal(FloatFilter total) {
        this.total = total;
    }

    public FloatFilter getPorcTarea() {
        return porcTarea;
    }

    public void setPorcTarea(FloatFilter porcTarea) {
        this.porcTarea = porcTarea;
    }

    public FloatFilter getPorcAdv() {
        return porcAdv;
    }

    public void setPorcAdv(FloatFilter porcAdv) {
        this.porcAdv = porcAdv;
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
        final AdvObraRepCriteria that = (AdvObraRepCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(obra, that.obra) &&
            Objects.equals(obraId, that.obraId) &&
            Objects.equals(concepto, that.concepto) &&
            Objects.equals(conceptoId, that.conceptoId) &&
            Objects.equals(taskName, that.taskName) &&
            Objects.equals(advanceStatus, that.advanceStatus) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(porcTarea, that.porcTarea) &&
            Objects.equals(porcAdv, that.porcAdv) &&
            Objects.equals(total, that.total) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            obra,
            obraId,
            concepto,
            conceptoId,
            taskName,
            quantity,
            cost,
            advanceStatus,
            total,
            porcTarea,
            porcAdv,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvObraRepCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (obra != null ? "obra=" + obra + ", " : "") +
            (obraId != null ? "obraId=" + obraId + ", " : "") +
            (concepto != null ? "subcontratista=" + concepto + ", " : "") +
            (conceptoId != null ? "subcontratista=" + conceptoId + ", " : "") +
            (taskName != null ? "subcontratista=" + taskName + ", " : "") +
            (quantity != null ? "subcontratista=" + quantity + ", " : "") +
            (cost != null ? "subcontratista=" + cost + ", " : "") +
            (advanceStatus != null ? "advanceStatus=" + advanceStatus + ", " : "") +
            (total != null ? "subcontratista=" + total + ", " : "") +
            (porcTarea != null ? "subcontratista=" + porcTarea + ", " : "") +
            (porcAdv != null ? "subcontratista=" + porcAdv + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
