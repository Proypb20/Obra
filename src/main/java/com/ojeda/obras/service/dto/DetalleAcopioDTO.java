package com.ojeda.obras.service.dto;

import com.ojeda.obras.domain.enumeration.Estado;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.DetalleAcopio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalleAcopioDTO implements Serializable {

    private Long id;

    private Instant date;

    private String description;

    @NotNull
    private Double quantity;

    @NotNull
    private Double unitPrice;

    @NotNull
    private Double amount;

    @NotNull
    private Instant requestDate;

    private Instant promiseDate;

    private Estado deliveryStatus;

    private AcopioDTO acopio;

    private DetalleListaPrecioDTO detalleListaPrecio;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Instant getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(Instant promiseDate) {
        this.promiseDate = promiseDate;
    }

    public Estado getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Estado deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public AcopioDTO getAcopio() {
        return acopio;
    }

    public void setAcopio(AcopioDTO acopio) {
        this.acopio = acopio;
    }

    public DetalleListaPrecioDTO getDetalleListaPrecio() {
        return detalleListaPrecio;
    }

    public void setDetalleListaPrecio(DetalleListaPrecioDTO detalleListaPrecio) {
        this.detalleListaPrecio = detalleListaPrecio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleAcopioDTO)) {
            return false;
        }

        DetalleAcopioDTO detalleAcopioDTO = (DetalleAcopioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detalleAcopioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleAcopioDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", amount=" + getAmount() +
            ", requestDate='" + getRequestDate() + "'" +
            ", promiseDate='" + getPromiseDate() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            ", acopio=" + getAcopio() +
            ", detalleListaPrecio=" + getDetalleListaPrecio() +
            "}";
    }
}
