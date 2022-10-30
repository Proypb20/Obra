package com.ojeda.obras.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubcoPayAmount.
 */
@Entity
@Table(name = "subco_pay_subcos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubcoPaySubco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "obra_id")
    private Long obraId;

    @Column(name = "obra_name")
    private String obraName;

    @Column(name = "subcontratista_id")
    private Long subcontratistaId;

    @Column(name = "subcontratista")
    private String subcontratista;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SubcoPaySubco id(Long id) {
        this.setId(id);
        return this;
    }

    public SubcoPaySubco obraId(Long obraId) {
        this.setObraId(obraId);
        return this;
    }

    public SubcoPaySubco obraName(String obraName) {
        this.setObraName(obraName);
        return this;
    }

    public SubcoPaySubco subcontratistaId(Long subcontratistaId) {
        this.setSubcontratistaId(subcontratistaId);
        return this;
    }

    public SubcoPaySubco subcontratista(String subcontratista) {
        this.setSubcontratista(subcontratista);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubcoPaySubco)) {
            return false;
        }
        return id != null && id.equals(((SubcoPaySubco) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubcoPaySubco{" +
            "id=" + getId() +
            ", obraId='" + getObraId().toString() + "'" +
            ", obraName='" + getObraName().toString() + "'" +
            ", subcontratistaId='" + getSubcontratistaId().toString() + "'" +
            ", subcontratista='" + getSubcontratista() + "'" +
            "}";
    }
}
