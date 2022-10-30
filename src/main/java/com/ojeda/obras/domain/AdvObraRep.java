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
    @Column(name = "total", nullable = false)
    private Double total;

    @NotNull
    @Column(name = "porc_tarea", nullable = false)
    private Double porcTarea;

    @NotNull
    @Column(name = "porc_adv", nullable = false)
    private Double porcAdv;

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

    public Double getTotal() {
        return total;
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

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setPorcTarea(Double porcTarea) {
        this.porcTarea = porcTarea;
    }

    public void setPorcAdv(Double porcAdv) {
        this.porcAdv = porcAdv;
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
            ", concepto='" + concepto  + "'" +
            ", conceptoId=" + conceptoId  + "'" +
            ", taskName='" + taskName  + "'" +
            ", quantity=" + quantity  + "'" +
            ", cost=" + cost  + "'" +
            ", advanceStatus=" + advanceStatus  + "'" +
            ", total=" + total  + "'" +
            ", porcTarea=" + porcTarea  + "'" +
            ", porcAdv=" + porcAdv  + "'" +
            '}';
    }
}
