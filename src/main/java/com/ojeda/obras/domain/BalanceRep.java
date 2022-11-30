package com.ojeda.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BalanceRep.
 */
@Entity
@Table(name = "balance_rep")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BalanceRep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "ingreso")
    private Double ingreso;

    @Column(name = "egreso")
    private Double egreso;

    @Column(name = "amount")
    private Double amount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

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

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getIngreso() {
        return ingreso;
    }

    public void setIngreso(Double ingreso) {
        this.ingreso = ingreso;
    }

    public Double getEgreso() {
        return egreso;
    }

    public void setEgreso(Double egreso) {
        this.egreso = egreso;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BalanceRep)) {
            return false;
        }
        return id != null && id.equals(((BalanceRep) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BalanceRep{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", metodoPago='" + getMetodoPago() + "'" +
            ", ingreso=" + getIngreso() +
            ", egreso=" + getEgreso() +
            ", amount=" + getAmount() +
            "}";
    }
}
