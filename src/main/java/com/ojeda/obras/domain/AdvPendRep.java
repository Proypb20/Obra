package com.ojeda.obras.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Concepto.
 */
@Entity
@Table(name = "advance_pending_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdvPendRep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "obra_id", nullable = false)
    private Long obraId;

    @NotNull
    @Column(name = "obra", nullable = false)
    private String obra;

    @NotNull
    @Column(name = "subcontratista_id", nullable = false)
    private Long subcontratistaId;

    @NotNull
    @Column(name = "subcontratista", nullable = false)
    private String subcontratista;

    @NotNull
    @Column(name = "advance_status", nullable = false)
    private Long advanceStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdvPendRep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObra() {
        return this.obra;
    }

    public String getSubcontratista() {
        return this.subcontratista;
    }

    public Long getAdvanceStatus() {
        return this.advanceStatus;
    }

    public AdvPendRep obra(String obra) {
        this.setObra(obra);
        return this;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public AdvPendRep subcontratista(String subcontratista) {
        this.setSubcontratista(subcontratista);
        return this;
    }

    public void setSubcontratista(String subcontratista) {
        this.subcontratista = subcontratista;
    }

    public AdvPendRep advanceStatus(Long advanceStatus) {
        this.setAdvanceStatus(advanceStatus);
        return this;
    }

    public void setAdvanceStatus(Long advanceStatus) {
        this.advanceStatus = advanceStatus;
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

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvPendRep)) {
            return false;
        }
        return id != null && id.equals(((AdvPendRep) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvPendRep{" +
            "id=" + getId() +
            ", obraId='" + getObraId() + "'" +
            ", obra='" + getObra() + "'" +
            ", subcontratistaId='" + getSubcontratistaId() + "'" +
            ", subcontratista='" + getSubcontratista() + "'" +
            ", advanceStatus='" + getAdvanceStatus().toString() + "'" +
            "}";
    }
}
