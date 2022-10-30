package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.SubcoPayAmount} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubcoPaySubcoDTO implements Serializable {

    private Long id;

    private Long obraId;

    private String obraName;

    private Long subcontratistaId;

    private String subcontratista;

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

    public Long getSubcontratistaId() {
        return subcontratistaId;
    }

    public void setSubcontratistaId(Long subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
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
        if (!(o instanceof SubcoPaySubcoDTO)) {
            return false;
        }

        SubcoPaySubcoDTO subcoPayAmountDTO = (SubcoPaySubcoDTO) o;
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
        return "SubcoPaySubcoDTO{" +
            "id=" + getId() + "'" +
            ", obraId='" + getObraId() + "'" +
            ", obraName='" + getObraName() + "'" +
            ", subcontratistaId='" + getSubcontratistaId() + "'" +
            ", subcontratista='" + getSubcontratista() + "'" +
            '}';
    }
}
