package com.ojeda.obras.service.dto;

import com.ojeda.obras.domain.enumeration.Estado;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.DetalleAcopio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalleAcopioDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String description;

    @NotNull
    private Float quantity;

    @NotNull
    private Float unitPrice;

    @NotNull
    private Float amount;

    @NotNull
    private LocalDate requestDate;

    private LocalDate promiseDate;

    private Estado deliveryStatus;

    private AcopioDTO acopio;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(LocalDate promiseDate) {
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
            "}";
    }
}
