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
@Table(name = "subco_pay_concepts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubcoPayConcept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "concepto_id")
    private Long conceptoId;

    @Column(name = "concepto_name")
    private String conceptoName;

    @Column(name = "sign")
    private String sign;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SubcoPayConcept id(Long id) {
        this.setId(id);
        return this;
    }

    public SubcoPayConcept conceptoId(Long conceptoId) {
        this.setConceptoId(conceptoId);
        return this;
    }

    public SubcoPayConcept conceptoName(String conceptoName) {
        this.setConceptoName(conceptoName);
        return this;
    }

    public SubcoPayConcept sign(String sign) {
        this.setSign(sign);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConceptoId() {
        return conceptoId;
    }

    public void setConceptoId(Long conceptoId) {
        this.conceptoId = conceptoId;
    }

    public String getConceptoName() {
        return conceptoName;
    }

    public void setConceptoName(String conceptoName) {
        this.conceptoName = conceptoName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubcoPayConcept)) {
            return false;
        }
        return id != null && id.equals(((SubcoPayConcept) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubcoPayConcept{" +
            "id=" + getId() +
            ", conceptoId='" + getConceptoId().toString() + "'" +
            ", conceptoName='" + getConceptoName() + "'" +
            ", sign='" + getSign() + "'" +
            "}";
    }
}
