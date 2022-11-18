package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.ListaPrecio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ListaPrecioDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Instant date;

    private ProveedorDTO proveedor;

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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
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
        if (!(o instanceof ListaPrecioDTO)) {
            return false;
        }

        ListaPrecioDTO listaPrecioDTO = (ListaPrecioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, listaPrecioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListaPrecioDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", proveedor=" + getProveedor() +
            "}";
    }
}
