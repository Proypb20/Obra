package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.SubcoPayAmount} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResObraDTO implements Serializable {

    private Long id;

    private String obraName;

    private Date date;

    private String periodName;

    private String source;

    private String reference;

    private String description;

    private String type;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(o instanceof ResObraDTO)) {
            return false;
        }

        ResObraDTO subcoPayAmountDTO = (ResObraDTO) o;
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
        return "ResObraDTO{" +
            "id=" + getId() + "'" +
            ", obraName='" + getObraName() + "'" +
            ", date='" + getDate() + "'" +
            ", periodName='" + getPeriodName() + "'" +
            ", source='" + getSource() + "'" +
            ", reference='" + getReference() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", amount='" + getAmount() + "'" +
            '}';
    }
}
