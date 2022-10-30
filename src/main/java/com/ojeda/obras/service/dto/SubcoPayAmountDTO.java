package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.SubcoPayAmount} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubcoPayAmountDTO implements Serializable {

    private Long id;

    private String conceptoName;

    private Long conceptoId;

    private Long obraId;

    private String obraName;

    private Long subcontratistaId;

    private String subcontratista;

    private Double cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConceptoName() {
        return conceptoName;
    }

    public void setConceptoName(String conceptoName) {
        this.conceptoName = conceptoName;
    }

    public Long getConceptoId() {
        return conceptoId;
    }

    public void setConceptoId(Long conceptoId) {
        this.conceptoId = conceptoId;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public Long getSubcontratistaId() {
        return subcontratistaId;
    }

    public void setSubcontratistaId(Long subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getObraName() {
        return obraName;
    }

    public void setObraName(String obraName) {
        this.obraName = obraName;
    }

    public String getSubcontratista() {
        return subcontratista;
    }

    public void setSubcontratista(String subcontratista) {
        this.subcontratista = subcontratista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubcoPayAmountDTO)) {
            return false;
        }

        SubcoPayAmountDTO subcoPayAmountDTO = (SubcoPayAmountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subcoPayAmountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubcoPayAmountDTO{" +
            "id=" + getId() + "'" +
            ", conceptoName=" + getConceptoName() + "'" +
            ", conceptoId=" + getConceptoId() + "'" +
            ", obraId='" + getObraId() + "'" +
            ", obraName='" + getObraName() + "'" +
            ", subcontratistaId='" + getSubcontratistaId() + "'" +
            ", subcontratista='" + getSubcontratista() + "'" +
            ", cost='" + getCost() + "'" +
            '}';
    }
}
