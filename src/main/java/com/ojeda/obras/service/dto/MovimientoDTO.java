package com.ojeda.obras.service.dto;

import com.ojeda.obras.domain.enumeration.MetodoPago;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Movimiento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MovimientoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    private String description;

    @NotNull
    private MetodoPago metodoPago;

    @NotNull
    private Double amount;

    private String transactionNumber;

    private ObraDTO obra;

    private SubcontratistaDTO subcontratista;

    private ConceptoDTO concepto;

    private TipoComprobanteDTO tipoComprobante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
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

    public ConceptoDTO getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptoDTO concepto) {
        this.concepto = concepto;
    }

    public TipoComprobanteDTO getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobanteDTO tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovimientoDTO)) {
            return false;
        }

        MovimientoDTO movimientoDTO = (MovimientoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, movimientoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovimientoDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", metodoPago='" + getMetodoPago() + "'" +
            ", amount=" + getAmount() +
            ", transactionNumber='" + getTransactionNumber() + "'" +
            ", obra=" + getObra() +
            ", subcontratista=" + getSubcontratista() +
            ", concepto=" + getConcepto() +
            ", tipoComprobante=" + getTipoComprobante() +
            "}";
    }
}
