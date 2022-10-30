package com.ojeda.obras.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubcoPayAmount.
 */
@Entity
@Table(name = "subco_pay_amounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubcoPayAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "concepto_name")
    private String conceptoName;

    @Column(name = "concepto_id")
    private Long conceptoId;

    @Column(name = "obra_id")
    private Long obraId;

    @Column(name = "obra_name")
    private String obraName;

    @Column(name = "subcontratista_id")
    private Long subcontratistaId;

    @Column(name = "subcontratista")
    private String subcontratista;

    @Column(name = "cost")
    private Double cost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SubcoPayAmount id(Long id) {
        this.setId(id);
        return this;
    }

    public SubcoPayAmount conceptoName(String conceptoName) {
        this.setConceptoName(conceptoName);
        return this;
    }

    public SubcoPayAmount conceptoId(Long conceptoId) {
        this.setConceptoId(conceptoId);
        return this;
    }

    public SubcoPayAmount obraId(Long obraId) {
        this.setObraId(obraId);
        return this;
    }

    public SubcoPayAmount obraName(String obraName) {
        this.setObraName(obraName);
        return this;
    }

    public SubcoPayAmount subcontratistaId(Long subcontratistaId) {
        this.setSubcontratistaId(subcontratistaId);
        return this;
    }

    public SubcoPayAmount subcontratista(String subcontratista) {
        this.setSubcontratista(subcontratista);
        return this;
    }

    public SubcoPayAmount cost(Double cost) {
        this.setCost(cost);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConceptoName() {
        return conceptoName;
    }

    public void setConceptoName(String conceptoName) {
        this.conceptoName = conceptoName;
    }

    public Long getConceptoId() {
        return conceptoId;
    }

    public void setConceptoId(Long conceptoId) {
        this.conceptoId = conceptoId;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public Long getSubcontratistaId() {
        return subcontratistaId;
    }

    public void setSubcontratistaId(Long subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
    }

    public String getObraName() {
        return obraName;
    }

    public void setObraName(String obraName) {
        this.obraName = obraName;
    }

    public String getSubcontratista() {
        return subcontratista;
    }

    public void setSubcontratista(String subcontratista) {
        this.subcontratista = subcontratista;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubcoPayAmount)) {
            return false;
        }
        return id != null && id.equals(((SubcoPayAmount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubcoPayAmount{" +
            "id=" + getId() +
            ", conceptoName='" + getConceptoName() + "'" +
            ", conceptoId='" + getConceptoId().toString() + "'" +
            ", obraId='" + getObraId().toString() + "'" +
            ", obraName='" + getObraName().toString() + "'" +
            ", subcontratistaId='" + getSubcontratistaId().toString() + "'" +
            ", subcontratista='" + getSubcontratista() + "'" +
            ", cost='" + getCost() + "'" +
            "}";
    }
}
