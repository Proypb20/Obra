package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Acopio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcopioDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Long totalAmount;

    private ObraDTO obra;

    private ProveedorDTO proveedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ObraDTO getObra() {
        return obra;
    }

    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }

    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcopioDTO)) {
            return false;
        }

        AcopioDTO acopioDTO = (AcopioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, acopioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcopioDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", obra=" + getObra() +
            ", proveedor=" + getProveedor() +
            "}";
    }
}
