package com.ojeda.obras.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Seguimiento.
 */
@Entity
@Table(name = "seguimiento_rep")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Seguimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "obra_name")
    private String obraName;

    @Column(name = "date")
    private Date date;

    @Column(name = "period_name")
    private String periodName;

    @Column(name = "source")
    private String source;

    @Column(name = "reference")
    private String reference;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private Double amount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObraName() {
        return obraName;
    }

    public void setObraName(String obraName) {
        this.obraName = obraName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(o instanceof Seguimiento)) {
            return false;
        }
        return id != null && id.equals(((Seguimiento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seguimiento{" +
            "id=" + getId() +
            ", obra='" + getObraName() + "'" +
            ", date='" + getDate() + "'" +
            ", periodName='" + getPeriodName() + "'" +
            ", source='" + getSource() + "'" +
            ", reference='" + getReference() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", amount='" + getAmount().toString() + "'" +
            "}";
    }
}
