package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.AdvObraRep} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdvObraRepDTO implements Serializable {

    private Long id;

    private String obra;

    private Long obraId;

    private String concepto;

    private Long conceptoId;

    private String taskName;

    private Float quantity;

    private Float cost;

    private Float advanceStatus;

    private Float total;

    private Float porcTarea;

    private Float porcAdv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getConcepto() {
        return concepto;
    }

    public Long getConceptoId() {
        return conceptoId;
    }

    public String getTaskName() {
        return taskName;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Float getCost() {
        return cost;
    }

    public Float getAdvanceStatus() {
        return advanceStatus;
    }

    public Float getTotal() {
        return total;
    }

    public Float getPorcTarea() {
        return porcTarea;
    }

    public Float getPorcAdv() {
        return porcAdv;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setConceptoId(Long conceptoId) {
        this.conceptoId = conceptoId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public void setAdvanceStatus(Float advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void setPorcTarea(Float porcTarea) {
        this.porcTarea = porcTarea;
    }

    public void setPorcAdv(Float porcAdv) {
        this.porcAdv = porcAdv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvObraRepDTO)) {
            return false;
        }

        AdvObraRepDTO advPendRepDTO = (AdvObraRepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, advPendRepDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvObraRepDTO{" +
            "id=" + getId() + "'" +
            ", obra='" + getObra() + "'" +
            ", obraId=" + getObraId() + "'" +
            ", concepto='" + getConcepto() + "'" +
            ", conceptoId=" + getConceptoId() + "'" +
            ", taskName='" + getTaskName() + "'" +
            ", quantity=" + getQuantity() + "'" +
            ", cost=" + getCost() + "'" +
            ", advanceStatus=" + getAdvanceStatus() + "'" +
            ", total=" + getTotal() + "'" +
            ", porcTarea=" + getPorcTarea() + "'" +
            ", porcAdv=" + getPorcAdv() + "'" +
            '}';
    }
}
