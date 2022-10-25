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
    private Float quantity;

    @NotNull
    @Column(name = "cost", nullable = false)
    private Float cost;

    @NotNull
    @Column(name = "advance_status", nullable = false)
    private Float advanceStatus;

    @NotNull
    @Column(name = "total", nullable = false)
    private Float total;

    @NotNull
    @Column(name = "porc_tarea", nullable = false)
    private Float porcTarea;

    @NotNull
    @Column(name = "porc_adv", nullable = false)
    private Float porcAdv;

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

    public Float getAdvanceStatus() {
        return this.advanceStatus;
    }

    public AdvObraRep obra(String obra) {
        this.setObra(obra);
        return this;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public AdvObraRep advanceStatus(Float advanceStatus) {
        this.setAdvanceStatus(advanceStatus);
        return this;
    }

    public void setAdvanceStatus(Float advanceStatus) {
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

    public Float getQuantity() {
        return quantity;
    }

    public Float getCost() {
        return cost;
    }

    public Float getTotal() {
        return total;
    }

    public Float getPorcTarea() {
        return porcTarea;
    }

    public Float getPorcAdv() {
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

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void setPorcTarea(Float porcTarea) {
        this.porcTarea = porcTarea;
    }

    public void setPorcAdv(Float porcAdv) {
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
