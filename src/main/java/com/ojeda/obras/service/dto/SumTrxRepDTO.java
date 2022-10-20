package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.AdvPendRep} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SumTrxRepDTO implements Serializable {

    private Long id;

    private Long obraId;

    private String obra;

    private LocalDate fecha;

    private String subcontratista;

    private String tipoComprobante;

    private String transactionNumber;

    private String concepto;

    private Float debitAmount;

    private Float creditAmount;

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

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getSubcontratista() {
        return subcontratista;
    }

    public void setSubcontratista(String subcontratista) {
        this.subcontratista = subcontratista;
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

    public Float getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Float debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Float getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Float creditAmount) {
        this.creditAmount = creditAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SumTrxRepDTO)) {
            return false;
        }

        SumTrxRepDTO advPendRepDTO = (SumTrxRepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, advPendRepDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvPendRepDTO{" +
            "id=" + getId() +
            ", obraId='" + getObraId() + "'" +
            ", obra='" + getObra() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", subcontratista='" + getSubcontratista() + "'" +
            ", tipoComprobante='" + getTipoComprobante() + "'" +
            ", transactionNumber='" + getTransactionNumber() + "'" +
            ", concepto='" + getConcepto() + "'" +
            ", debitAmount='" + getDebitAmount() + "'" +
            ", creditAmount='" + getCreditAmount() + "'" +
            "}";
    }
}
