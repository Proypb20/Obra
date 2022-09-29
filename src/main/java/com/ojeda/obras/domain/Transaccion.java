package com.ojeda.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ojeda.obras.domain.enumeration.MetodoPago;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transaccion.
 */
@Entity
@Table(name = "transaccion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private MetodoPago paymentMethod;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = { "provincia", "subcontratistas" }, allowSetters = true)
    private Obra obra;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras" }, allowSetters = true)
    private Subcontratista subcontratista;

    @ManyToOne
    private TipoComprobante tipoComprobante;

    @ManyToOne
    private Concepto concepto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transaccion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Transaccion date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MetodoPago getPaymentMethod() {
        return this.paymentMethod;
    }

    public Transaccion paymentMethod(MetodoPago paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(MetodoPago paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionNumber() {
        return this.transactionNumber;
    }

    public Transaccion transactionNumber(String transactionNumber) {
        this.setTransactionNumber(transactionNumber);
        return this;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Float getAmount() {
        return this.amount;
    }

    public Transaccion amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getNote() {
        return this.note;
    }

    public Transaccion note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Obra getObra() {
        return this.obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Transaccion obra(Obra obra) {
        this.setObra(obra);
        return this;
    }

    public Subcontratista getSubcontratista() {
        return this.subcontratista;
    }

    public void setSubcontratista(Subcontratista subcontratista) {
        this.subcontratista = subcontratista;
    }

    public Transaccion subcontratista(Subcontratista subcontratista) {
        this.setSubcontratista(subcontratista);
        return this;
    }

    public TipoComprobante getTipoComprobante() {
        return this.tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public Transaccion tipoComprobante(TipoComprobante tipoComprobante) {
        this.setTipoComprobante(tipoComprobante);
        return this;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Transaccion concepto(Concepto concepto) {
        this.setConcepto(concepto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaccion)) {
            return false;
        }
        return id != null && id.equals(((Transaccion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaccion{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", transactionNumber='" + getTransactionNumber() + "'" +
            ", amount=" + getAmount() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
