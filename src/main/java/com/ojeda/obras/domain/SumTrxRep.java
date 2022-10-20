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
@Table(name = "sum_trx_rep")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SumTrxRep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "obra_id")
    private Long obraId;

    @Column(name = "obra")
    private String obra;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "subcontratista")
    private String subcontratista;

    @Column(name = "tipo_comprobante")
    private String tipoComprobante;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "debit_amount")
    private Float debitAmount;

    @Column(name = "credit_amount")
    private Float creditAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SumTrxRep id(Long id) {
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

    public SumTrxRep obra(String obra) {
        this.setObra(obra);
        return this;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public SumTrxRep subcontratista(String subcontratista) {
        this.setSubcontratista(subcontratista);
        return this;
    }

    public void setSubcontratista(String subcontratista) {
        this.subcontratista = subcontratista;
    }

    public Float getDebitAmount() {
        return this.debitAmount;
    }

    public SumTrxRep debitAmount(Float debitAmount) {
        this.setDebitAmount(debitAmount);
        return this;
    }

    public void setDebitAmount(Float debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Float getCreditAmount() {
        return this.creditAmount;
    }

    public SumTrxRep creditAmount(Float creditAmount) {
        this.setCreditAmount(creditAmount);
        return this;
    }

    public void setCreditAmount(Float creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public SumTrxRep obraId(Long obraId) {
        this.setObraId(obraId);
        return this;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SumTrxRep)) {
            return false;
        }
        return id != null && id.equals(((SumTrxRep) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SumTrxRep{" +
            "id=" + getId() +
            ", obraId='" + getObraId() + "'" +
            ", obra='" + getObra() + "'" +
            ", subcontratista='" + getSubcontratista() + "'" +
            ", debitAmount='" + getDebitAmount().toString() + "'" +
            ", creditAmount='" + getCreditAmount().toString() + "'" +
            "}";
    }
}
