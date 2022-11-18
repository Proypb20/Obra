package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Acopio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcopioDTO implements Serializable {

    private Long id;

    private Instant date;

    private Double totalAmount;

    private ObraDTO obra;

    private ListaPrecioDTO listaprecio;

    private ProveedorDTO proveedor;

    private Double saldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ObraDTO getObra() {
        return obra;
    }

    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }

    public ListaPrecioDTO getListaprecio() {
        return listaprecio;
    }

    public void setListaprecio(ListaPrecioDTO listaprecio) {
        this.listaprecio = listaprecio;
    }

    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
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
            ", listaprecio=" + getListaprecio() +
            ", proveedor=" + getProveedor() +
            "}";
    }
}
