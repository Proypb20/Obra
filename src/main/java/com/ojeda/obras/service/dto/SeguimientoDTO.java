package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Seguimiento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SeguimientoDTO implements Serializable {

    private Long id;

    private String obraName;

    private String periodName;

    private String conceptName;

    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObraName() {
        return obraName;
    }

    public void setObraName(String obraName) {
        this.obraName = obraName;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeguimientoDTO)) {
            return false;
        }

        SeguimientoDTO subcoPayAmountDTO = (SeguimientoDTO) o;
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
        return "SeguimientoDTO{" +
            "id=" + getId() + "'" +
            ", obraName='" + getObraName() + "'" +
            ", periodName='" + getPeriodName() + "'" +
            ", conceptName='" + getConceptName() + "'" +
            ", amount='" + getAmount() + "'" +
            '}';
    }
}
