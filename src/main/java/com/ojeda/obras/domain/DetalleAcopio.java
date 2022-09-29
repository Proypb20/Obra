package com.ojeda.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ojeda.obras.domain.enumeration.Estado;
import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Float amount;

    @NotNull
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "promise_date")
    private LocalDate promiseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private Estado deliveryStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obra", "proveedor" }, allowSetters = true)
    private Acopio acopio;

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

    public LocalDate getDate() {
        return this.date;
    }

    public DetalleAcopio date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
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

    public Float getQuantity() {
        return this.quantity;
    }

    public DetalleAcopio quantity(Float quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return this.unitPrice;
    }

    public DetalleAcopio unitPrice(Float unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getAmount() {
        return this.amount;
    }

    public DetalleAcopio amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public DetalleAcopio requestDate(LocalDate requestDate) {
        this.setRequestDate(requestDate);
        return this;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getPromiseDate() {
        return this.promiseDate;
    }

    public DetalleAcopio promiseDate(LocalDate promiseDate) {
        this.setPromiseDate(promiseDate);
        return this;
    }

    public void setPromiseDate(LocalDate promiseDate) {
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
