package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Concepto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConceptoDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptoDTO)) {
            return false;
        }

        ConceptoDTO conceptoDTO = (ConceptoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, conceptoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
