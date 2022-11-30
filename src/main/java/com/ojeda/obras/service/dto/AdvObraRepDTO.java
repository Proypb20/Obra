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

    private String subcontratista;

    private Long subcontratistaId;

    private String concepto;

    private Long conceptoId;

    private String taskName;

    private Float quantity;

    private Float cost;

    private Float advanceStatus;

    private Float totalObra;

    private Float totalSubco;

    private Float porcTarea;

    private Float porcTareaSubco;

    private Float porcAdv;

    private Float porcAdvSubco;

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

    public String getSubcontratista() {
        return subcontratista;
    }

    public void setSubcontratista(String subcontratista) {
        this.subcontratista = subcontratista;
    }

    public Long getSubcontratistaId() {
        return subcontratistaId;
    }

    public void setSubcontratistaId(Long subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
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

    public Float getTotalObra() {
        return totalObra;
    }

    public Float getTotalSubco() {
        return totalSubco;
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

    public void setTotal(Float totalObra) {
        this.totalObra = totalObra;
    }

    public void setTotalObra(Float totalObra) {
        this.totalObra = totalObra;
    }

    public void setTotalSubco(Float totalSubco) {
        this.totalSubco = totalSubco;
    }

    public void setPorcTarea(Float porcTarea) {
        this.porcTarea = porcTarea;
    }

    public void setPorcAdv(Float porcAdv) {
        this.porcAdv = porcAdv;
    }

    public Float getPorcTareaSubco() {
        return porcTareaSubco;
    }

    public void setPorcTareaSubco(Float porcTareaSubco) {
        this.porcTareaSubco = porcTareaSubco;
    }

    public Float getPorcAdvSubco() {
        return porcAdvSubco;
    }

    public void setPorcAdvSubco(Float porcAdvSubco) {
        this.porcAdvSubco = porcAdvSubco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvObraRepDTO)) {
            return false;
        }

        AdvObraRepDTO advObraRepDTO = (AdvObraRepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, advObraRepDTO.id);
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
            ", subcontratista='" + getSubcontratista() + "'" +
            ", subcontratistaId=" + getSubcontratistaId() + "'" +
            ", concepto='" + getConcepto() + "'" +
            ", conceptoId=" + getConceptoId() + "'" +
            ", taskName='" + getTaskName() + "'" +
            ", quantity=" + getQuantity() + "'" +
            ", cost=" + getCost() + "'" +
            ", advanceStatus=" + getAdvanceStatus() + "'" +
            ", totalObra=" + getTotalObra() + "'" +
            ", totalSubco=" + getTotalSubco() + "'" +
            ", porcTarea=" + getPorcTarea() + "'" +
            ", porcTareaSubco=" + getPorcTareaSubco() + "'" +
            ", porcAdv=" + getPorcAdv() + "'" +
            ", porcAdvSubco=" + getPorcAdv() + "'" +
            '}';
    }
}
