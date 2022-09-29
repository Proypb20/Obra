package com.ojeda.obras.service.dto;

import com.ojeda.obras.domain.enumeration.MetodoPago;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Transaccion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransaccionDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private MetodoPago paymentMethod;

    private String transactionNumber;

    private Float amount;

    private String note;

    private ObraDTO obra;

    private SubcontratistaDTO subcontratista;

    private TipoComprobanteDTO tipoComprobante;

    private ConceptoDTO concepto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MetodoPago getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(MetodoPago paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ObraDTO getObra() {
        return obra;
    }

    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }

    public SubcontratistaDTO getSubcontratista() {
        return subcontratista;
    }

    public void setSubcontratista(SubcontratistaDTO subcontratista) {
        this.subcontratista = subcontratista;
    }

    public TipoComprobanteDTO getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobanteDTO tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public ConceptoDTO getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptoDTO concepto) {
        this.concepto = concepto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransaccionDTO)) {
            return false;
        }

        TransaccionDTO transaccionDTO = (TransaccionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transaccionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransaccionDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", transactionNumber='" + getTransactionNumber() + "'" +
            ", amount=" + getAmount() +
            ", note='" + getNote() + "'" +
            ", obra=" + getObra() +
            ", subcontratista=" + getSubcontratista() +
            ", tipoComprobante=" + getTipoComprobante() +
            ", concepto=" + getConcepto() +
            "}";
    }
}
