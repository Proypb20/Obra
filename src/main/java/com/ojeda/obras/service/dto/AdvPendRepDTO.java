package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.ojeda.obras.domain.AdvPendRep} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdvPendRepDTO implements Serializable {

    private Long id;

    private Long obraId;

    private String obra;

    private Long subcontratistaId;

    private String subcontratista;

    private Long advanceStatus;

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

    public Long getSubcontratistaId() {
        return subcontratistaId;
    }

    public void setSubcontratistaId(Long subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
    }

    public String getSubcontratista() {
        return subcontratista;
    }

    public void setSubcontratista(String subcontratista) {
        this.subcontratista = subcontratista;
    }

    public Long getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(Long advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvPendRepDTO)) {
            return false;
        }

        AdvPendRepDTO advPendRepDTO = (AdvPendRepDTO) o;
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
        return "AdvPendRepDTO{" +
            "id=" + getId() +
            ", obraId='" + getObraId() + "'" +
            ", obra='" + getObra() + "'" +
            ", subcontratistaId='" + getSubcontratistaId() + "'" +
            ", subcontratista='" + getSubcontratista() + "'" +
            "}";
    }
}
