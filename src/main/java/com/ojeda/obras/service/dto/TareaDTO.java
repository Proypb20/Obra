package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Tarea} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TareaDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Float quantity;

    private Float cost;

    @Max(value = 100)
    private Float advanceStatus;

    private ObraDTO obra;

    private SubcontratistaDTO subcontratista;

    private ConceptoDTO concepto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(Float advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public ObraDTO getObra() {
        return obra;
    }

    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }

    public SubcontratistaDTO getSubcontratista() {
        return subcontratista;
    }

    public void setSubcontratista(SubcontratistaDTO subcontratista) {
        this.subcontratista = subcontratista;
    }

    public ConceptoDTO getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptoDTO concepto) {
        this.concepto = concepto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TareaDTO)) {
            return false;
        }

        TareaDTO tareaDTO = (TareaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tareaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TareaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", cost=" + getCost() +
            ", advanceStatus=" + getAdvanceStatus() +
            ", obra=" + getObra() +
            ", subcontratista=" + getSubcontratista() +
            ", concepto=" + getConcepto() +
            "}";
    }
}
