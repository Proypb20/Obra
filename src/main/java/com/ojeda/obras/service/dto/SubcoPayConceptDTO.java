package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.AdvObraRep} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubcoPayConceptDTO implements Serializable {

    private Long id;

    private Long conceptoId;

    private String conceptoName;

    private String sign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConceptoId() {
        return conceptoId;
    }

    public void setConceptoId(Long conceptoId) {
        this.conceptoId = conceptoId;
    }

    public String getConceptoName() {
        return conceptoName;
    }

    public void setConceptoName(String conceptoName) {
        this.conceptoName = conceptoName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubcoPayConceptDTO)) {
            return false;
        }

        SubcoPayConceptDTO subcoPayConceptDTO = (SubcoPayConceptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subcoPayConceptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubcoPayConceptDTO{" +
            "id=" + getId() + "'" +
            ", conceptoId=" + getConceptoId() + "'" +
            ", concepto='" + getConceptoName() + "'" +
            ", sign='" + getSign() + "'" +
            '}';
    }
}
