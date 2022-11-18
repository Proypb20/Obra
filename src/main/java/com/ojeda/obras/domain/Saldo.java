package com.ojeda.obras.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SumTrx.
 */
@Entity
@Table(name = "saldos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Saldo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "saldo")
    private Double saldo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Saldo saldo(Double saldo) {
        this.setSaldo(saldo);
        return this;
    }

    public Saldo metodoPago(String metodoPago) {
        this.setMetodoPago(metodoPago);
        return this;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Saldo)) {
            return false;
        }
        return metodoPago != null && metodoPago.equals(((Saldo) o).metodoPago);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Saldo{" +
            "metodoPago=" + getMetodoPago() +
            ", saldo='" + getSaldo() + "'" +
            "}";
    }
}
