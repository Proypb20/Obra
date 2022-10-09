package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.DetalleListaPrecio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalleListaPrecioDTO implements Serializable {

    private Long id;

    private String product;

    private Float amount;

    private ListaPrecioDTO listaPrecio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public ListaPrecioDTO getListaPrecio() {
        return listaPrecio;
    }

    public void setListaPrecio(ListaPrecioDTO listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleListaPrecioDTO)) {
            return false;
        }

        DetalleListaPrecioDTO detalleListaPrecioDTO = (DetalleListaPrecioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detalleListaPrecioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleListaPrecioDTO{" +
            "id=" + getId() +
            ", product='" + getProduct() + "'" +
            ", amount=" + getAmount() +
            ", listaPrecio=" + getListaPrecio() +
            "}";
    }
}
