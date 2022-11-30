package com.ojeda.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ojeda.obras.domain.enumeration.Estado;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DetalleAcopio.
 */
@Entity
@Table(name = "detalle_acopio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalleAcopio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "request_date", nullable = false)
    private Instant requestDate;

    @Column(name = "promise_date")
    private Instant promiseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private Estado deliveryStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obra", "listaprecio", "proveedor" }, allowSetters = true)
    private Acopio acopio;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listaPrecio" }, allowSetters = true)
    private DetalleListaPrecio detalleListaPrecio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetalleAcopio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public DetalleAcopio date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public DetalleAcopio description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public DetalleAcopio quantity(Double quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public DetalleAcopio unitPrice(Double unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getAmount() {
        return this.amount;
    }

    public DetalleAcopio amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getRequestDate() {
        return this.requestDate;
    }

    public DetalleAcopio requestDate(Instant requestDate) {
        this.setRequestDate(requestDate);
        return this;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Instant getPromiseDate() {
        return this.promiseDate;
    }

    public DetalleAcopio promiseDate(Instant promiseDate) {
        this.setPromiseDate(promiseDate);
        return this;
    }

    public void setPromiseDate(Instant promiseDate) {
        this.promiseDate = promiseDate;
    }

    public Estado getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public DetalleAcopio deliveryStatus(Estado deliveryStatus) {
        this.setDeliveryStatus(deliveryStatus);
        return this;
    }

    public void setDeliveryStatus(Estado deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Acopio getAcopio() {
        return this.acopio;
    }

    public void setAcopio(Acopio acopio) {
        this.acopio = acopio;
    }

    public DetalleAcopio acopio(Acopio acopio) {
        this.setAcopio(acopio);
        return this;
    }

    public DetalleListaPrecio getDetalleListaPrecio() {
        return this.detalleListaPrecio;
    }

    public void setDetalleListaPrecio(DetalleListaPrecio detalleListaPrecio) {
        this.detalleListaPrecio = detalleListaPrecio;
    }

    public DetalleAcopio detalleListaPrecio(DetalleListaPrecio detalleListaPrecio) {
        this.setDetalleListaPrecio(detalleListaPrecio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleAcopio)) {
            return false;
        }
        return id != null && id.equals(((DetalleAcopio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleAcopio{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", amount=" + getAmount() +
            ", requestDate='" + getRequestDate() + "'" +
            ", promiseDate='" + getPromiseDate() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            "}";
    }
}
