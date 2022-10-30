package com.ojeda.obras.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.SumTrxRep} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.SumTrxRepResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conceptos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SumTrxRepCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter obraId;

    private StringFilter obra;

    private LocalDateFilter fecha;

    private StringFilter subcontratista;

    private StringFilter tipoComprobante;

    private StringFilter transactionNumber;

    private StringFilter concepto;

    private DoubleFilter debitAmount;

    private DoubleFilter creditAmount;

    private Boolean distinct;

    public SumTrxRepCriteria() {}

    public SumTrxRepCriteria(SumTrxRepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.obraId = other.obraId == null ? null : other.obraId.copy();
        this.obra = other.obra == null ? null : other.obra.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.subcontratista = other.subcontratista == null ? null : other.subcontratista.copy();
        this.tipoComprobante = other.tipoComprobante == null ? null : other.tipoComprobante.copy();
        this.transactionNumber = other.transactionNumber == null ? null : other.transactionNumber.copy();
        this.concepto = other.concepto == null ? null : other.concepto.copy();
        this.debitAmount = other.debitAmount == null ? null : other.debitAmount.copy();
        this.creditAmount = other.creditAmount == null ? null : other.creditAmount.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SumTrxRepCriteria copy() {
        return new SumTrxRepCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getObraId() {
        return obraId;
    }

    public StringFilter getObra() {
        return obra;
    }

    public LocalDateFilter getFecha() {
        return fecha;
    }

    public StringFilter getSubcontratista() {
        return subcontratista;
    }

    public StringFilter getTipoComprobante() {
        return tipoComprobante;
    }

    public StringFilter getTransactionNumber() {
        return transactionNumber;
    }

    public StringFilter getConcepto() {
        return concepto;
    }

    public DoubleFilter getDebitAmount() {
        return debitAmount;
    }

    public DoubleFilter getCreditAmount() {
        return creditAmount;
    }

    public LongFilter obraId() {
        if (obraId == null) {
            obraId = new LongFilter();
        }
        return obraId;
    }

    public StringFilter obra() {
        if (obra == null) {
            obra = new StringFilter();
        }
        return obra;
    }

    public LocalDateFilter fecha() {
        if (fecha == null) {
            fecha = new LocalDateFilter();
        }
        return fecha;
    }

    public StringFilter subcontratista() {
        if (subcontratista == null) {
            subcontratista = new StringFilter();
        }
        return subcontratista;
    }

    public StringFilter tipoComprobante() {
        if (tipoComprobante == null) {
            tipoComprobante = new StringFilter();
        }
        return tipoComprobante;
    }

    public void setObraId(LongFilter obraId) {
        this.obraId = obraId;
    }

    public void setObra(StringFilter obra) {
        this.obra = obra;
    }

    public void setFecha(LocalDateFilter fecha) {
        this.fecha = fecha;
    }

    public void setSubcontratista(StringFilter subcontratista) {
        this.subcontratista = subcontratista;
    }

    public void setTipoComprobante(StringFilter tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SumTrxRepCriteria that = (SumTrxRepCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(obraId, that.obraId) &&
            Objects.equals(obra, that.obra) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(subcontratista, that.subcontratista) &&
            Objects.equals(tipoComprobante, that.tipoComprobante) &&
            Objects.equals(transactionNumber, that.transactionNumber) &&
            Objects.equals(concepto, that.concepto) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            obraId,
            obra,
            fecha,
            subcontratista,
            tipoComprobante,
            transactionNumber,
            concepto,
            debitAmount,
            creditAmount,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SumTrxRepCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (obraId != null ? "obraId=" + obraId + ", " : "") +
            (obra != null ? "obra=" + obra + ", " : "") +
            (fecha != null ? "fecha=" + fecha + ", " : "") +
            (subcontratista != null ? "subcontratista=" + subcontratista + ", " : "") +
            (transactionNumber != null ? "transactionNumber=" + transactionNumber + ", " : "") +
            (concepto != null ? "concepto=" + concepto + ", " : "") +
            (debitAmount != null ? "debitAmount=" + debitAmount + ", " : "") +
            (creditAmount != null ? "creditAmount=" + creditAmount + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
