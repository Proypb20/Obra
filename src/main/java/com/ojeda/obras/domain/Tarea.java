package com.ojeda.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tarea.
 */
@Entity
@Table(name = "tarea")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "cost")
    private Float cost;

    @Max(value = 100)
    @Column(name = "advance_status")
    private Float advanceStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "provincia", "subcontratistas" }, allowSetters = true)
    private Obra obra;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras" }, allowSetters = true)
    private Subcontratista subcontratista;

    @ManyToOne
    private Concepto concepto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tarea id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Tarea name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getQuantity() {
        return this.quantity;
    }

    public Tarea quantity(Float quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return this.cost;
    }

    public Tarea cost(Float cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getAdvanceStatus() {
        return this.advanceStatus;
    }

    public Tarea advanceStatus(Float advanceStatus) {
        this.setAdvanceStatus(advanceStatus);
        return this;
    }

    public void setAdvanceStatus(Float advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public Obra getObra() {
        return this.obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Tarea obra(Obra obra) {
        this.setObra(obra);
        return this;
    }

    public Subcontratista getSubcontratista() {
        return this.subcontratista;
    }

    public void setSubcontratista(Subcontratista subcontratista) {
        this.subcontratista = subcontratista;
    }

    public Tarea subcontratista(Subcontratista subcontratista) {
        this.setSubcontratista(subcontratista);
        return this;
    }

    public Concepto getConcepto() {
        return this.concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Tarea concepto(Concepto concepto) {
        this.setConcepto(concepto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarea)) {
            return false;
        }
        return id != null && id.equals(((Tarea) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tarea{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", cost=" + getCost() +
            ", advanceStatus=" + getAdvanceStatus() +
            "}";
    }
}
