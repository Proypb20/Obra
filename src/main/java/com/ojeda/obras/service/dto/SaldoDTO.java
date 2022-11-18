package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Saldo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaldoDTO implements Serializable {

    private String metodoPago;

    private Double saldo;

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaldoDTO)) {
            return false;
        }

        SaldoDTO saldo = (SaldoDTO) o;
        if (this.metodoPago == null) {
            return false;
        }
        return Objects.equals(this.metodoPago, saldo.metodoPago);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.metodoPago);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaldoDTO{" +
            "metodoPago=" + getMetodoPago() +
            ", saldo='" + getSaldo() + "'" +
            "}";
    }
}
