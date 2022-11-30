package com.ojeda.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ojeda.obras.service.AcopioService;
import com.ojeda.obras.service.DetalleAcopioService;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Acopio.
 */
@Entity
@Table(name = "acopio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Acopio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "saldo")
    private Double saldo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "provincia", "subcontratistas", "clientes" }, allowSetters = true)
    private Obra obra;

    @ManyToOne
    @JsonIgnoreProperties(value = { "proveedor" }, allowSetters = true)
    private ListaPrecio listaprecio;

    @ManyToOne
    @JsonIgnoreProperties(value = { "provincia" }, allowSetters = true)
    private Proveedor proveedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Acopio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Acopio date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public Acopio totalAmount(Double totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public Acopio saldo(Double saldo) {
        this.setSaldo(saldo);
        return this;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Obra getObra() {
        return this.obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Acopio obra(Obra obra) {
        this.setObra(obra);
        return this;
    }

    public ListaPrecio getListaprecio() {
        return this.listaprecio;
    }

    public void setListaprecio(ListaPrecio ListaPrecio) {
        this.listaprecio = ListaPrecio;
    }

    public Acopio listaprecio(ListaPrecio ListaPrecio) {
        this.setListaprecio(ListaPrecio);
        return this;
    }

    public Proveedor getProveedor() {
        return this.proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Acopio proveedor(Proveedor proveedor) {
        this.setProveedor(proveedor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acopio)) {
            return false;
        }
        return id != null && id.equals(((Acopio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Acopio{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", saldo=" + getSaldo() +
            "}";
    }
}
