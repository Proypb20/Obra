package com.ojeda.obras.service.dto;

import com.ojeda.obras.domain.enumeration.MetodoPago;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.BalanceRep} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BalanceRepDTO implements Serializable {

    private Long id;

    private String date;

    private MetodoPago metodoPago;

    private Double ingreso;

    private Double egreso;

    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getIngreso() {
        return ingreso;
    }

    public void setIngreso(Double ingreso) {
        this.ingreso = ingreso;
    }

    public Double getEgreso() {
        return egreso;
    }

    public void setEgreso(Double egreso) {
        this.egreso = egreso;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BalanceRepDTO)) {
            return false;
        }

        BalanceRepDTO balanceRepDTO = (BalanceRepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, balanceRepDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BalanceRepDTO{" +
            "id=" + getId() + "'" +
            ", date='" + getDate() + "'" +
            ", metodoPago=" + getMetodoPago() + "'" +
            ", ingreso='" + getIngreso() + "'" +
            ", egreso=" + getEgreso() + "'" +
            ", amount='" + getAmount() + "'" +
            '}';
    }
}
