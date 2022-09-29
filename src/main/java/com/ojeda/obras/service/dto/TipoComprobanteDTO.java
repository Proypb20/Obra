package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.TipoComprobante} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoComprobanteDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String sign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoComprobanteDTO)) {
            return false;
        }

        TipoComprobanteDTO tipoComprobanteDTO = (TipoComprobanteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoComprobanteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoComprobanteDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sign='" + getSign() + "'" +
            "}";
    }
}
