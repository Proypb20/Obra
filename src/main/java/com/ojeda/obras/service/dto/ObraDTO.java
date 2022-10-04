package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Obra} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObraDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String address;

    private String city;

    private String comments;

    private ProvinciaDTO provincia;

    private Set<SubcontratistaDTO> subcontratistas = new HashSet<>();

    private Set<ClienteDTO> clientes = new HashSet<>();

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProvinciaDTO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaDTO provincia) {
        this.provincia = provincia;
    }

    public Set<SubcontratistaDTO> getSubcontratistas() {
        return subcontratistas;
    }

    public void setSubcontratistas(Set<SubcontratistaDTO> subcontratistas) {
        this.subcontratistas = subcontratistas;
    }

    public Set<ClienteDTO> getClientes() {
        return clientes;
    }

    public void setClientes(Set<ClienteDTO> clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObraDTO)) {
            return false;
        }

        ObraDTO obraDTO = (ObraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, obraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObraDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", comments='" + getComments() + "'" +
            ", provincia=" + getProvincia() +
            ", subcontratistas=" + getSubcontratistas() +
            ", clientes=" + getClientes() +
            "}";
    }
}
