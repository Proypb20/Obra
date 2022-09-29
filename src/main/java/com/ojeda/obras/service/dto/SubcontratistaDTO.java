package com.ojeda.obras.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ojeda.obras.domain.Subcontratista} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubcontratistaDTO implements Serializable {

    private Long id;

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    private String phone;

    private String email;

    private Set<ObraDTO> obras = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ObraDTO> getObras() {
        return obras;
    }

    public void setObras(Set<ObraDTO> obras) {
        this.obras = obras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubcontratistaDTO)) {
            return false;
        }

        SubcontratistaDTO subcontratistaDTO = (SubcontratistaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subcontratistaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubcontratistaDTOXXX{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", obras=" + getObras() +
            "}";
    }
}
