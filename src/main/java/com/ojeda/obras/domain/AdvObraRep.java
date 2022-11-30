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
@Table(name = "adv_obra_rep")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdvObraRep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "obra", nullable = false)
    private String obra;

    @NotNull
    @Column(name = "obra_id", nullable = false)
    private Long obraId;

    @NotNull
    @Column(name = "subcontratista", nullable = false)
    private String subcontratista;

    @NotNull
    @Column(name = "subcontratista_id", nullable = false)
    private Long subcontratistaId;

    @NotNull
    @Column(name = "concepto", nullable = false)
    private String concepto;

    @NotNull
    @Column(name = "concepto_id", nullable = false)
    private Long conceptoId;

    @NotNull
    @Column(name = "task_name", nullable = false)
    private String taskName;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @NotNull
    @Column(name = "cost", nullable = false)
    private Double cost;

    @NotNull
    @Column(name = "advance_status", nullable = false)
    private Double advanceStatus;

    @NotNull
    @Column(name = "total_obra", nullable = false)
    private Double totalObra;

    @NotNull
    @Column(name = "total_subco", nullable = false)
    private Double totalSubco;

    @NotNull
    @Column(name = "porc_tarea", nullable = false)
    private Double porcTarea;

    @NotNull
    @Column(name = "porc_tarea_subco", nullable = false)
    private Double porcTareaSubco;

    @NotNull
    @Column(name = "porc_adv", nullable = false)
    private Double porcAdv;

    @NotNull
    @Column(name = "porc_adv_subco", nullable = false)
    private Double porcAdvSubco;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdvObraRep id(Long id) {
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

    public Double getAdvanceStatus() {
        return this.advanceStatus;
    }

    public AdvObraRep obra(String obra) {
        this.setObra(obra);
        return this;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public AdvObraRep subcontratista(String subcontratista) {
        this.setSubcontratista(subcontratista);
        return this;
    }

    public void setSubcontratista(String subcontratista) {
        this.subcontratista = subcontratista;
    }

    public AdvObraRep advanceStatus(Double advanceStatus) {
        this.setAdvanceStatus(advanceStatus);
        return this;
    }

    public void setAdvanceStatus(Double advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public String getConcepto() {
        return concepto;
    }

    public Long getConceptoId() {
        return conceptoId;
    }

    public String getTaskName() {
        return taskName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getCost() {
        return cost;
    }

    public Double getTotalObra() {
        return totalObra;
    }

    public Double getTotalSubco() {
        return totalSubco;
    }

    public Double getPorcTarea() {
        return porcTarea;
    }

    public Double getPorcAdv() {
        return porcAdv;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setConceptoId(Long conceptoId) {
        this.conceptoId = conceptoId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setTotalObra(Double totalObra) {
        this.totalObra = totalObra;
    }

    public void setTotalSubco(Double totalSubco) {
        this.totalSubco = totalSubco;
    }

    public void setPorcTarea(Double porcTarea) {
        this.porcTarea = porcTarea;
    }

    public void setPorcAdv(Double porcAdv) {
        this.porcAdv = porcAdv;
    }

    public Long getSubcontratistaId() {
        return subcontratistaId;
    }

    public void setSubcontratistaId(Long subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
    }

    public Double getPorcTareaSubco() {
        return porcTareaSubco;
    }

    public void setPorcTareaSubco(Double porcTareaSubco) {
        this.porcTareaSubco = porcTareaSubco;
    }

    public Double getPorcAdvSubco() {
        return porcAdvSubco;
    }

    public void setPorcAdvSubco(Double porcAdvSubco) {
        this.porcAdvSubco = porcAdvSubco;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvObraRep)) {
            return false;
        }
        return id != null && id.equals(((AdvObraRep) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvObraRep{" +
            "id=" + id  + "'" +
            ", obra='" + obra  + "'" +
            ", obraId=" + obraId  + "'" +
            ", subcontratista='" + subcontratista  + "'" +
            ", subcontratistaId=" + subcontratistaId  + "'" +
            ", concepto='" + concepto  + "'" +
            ", conceptoId=" + conceptoId  + "'" +
            ", taskName='" + taskName  + "'" +
            ", quantity=" + quantity  + "'" +
            ", cost=" + cost  + "'" +
            ", advanceStatus=" + advanceStatus  + "'" +
            ", totalObra=" + totalObra  + "'" +
            ", totalSubco=" + totalSubco  + "'" +
            ", porcTarea=" + porcTarea  + "'" +
            ", porcTareaSubco=" + porcTareaSubco  + "'" +
            ", porcAdv=" + porcAdv  + "'" +
            ", porcAdvSubco=" + porcAdvSubco  + "'" +
            '}';
    }
}
