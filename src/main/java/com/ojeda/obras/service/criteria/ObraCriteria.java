package com.ojeda.obras.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ojeda.obras.domain.Obra} entity. This class is used
 * in {@link com.ojeda.obras.web.rest.ObraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /obras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter address;

    private StringFilter city;

    private StringFilter comments;

    private LongFilter provinciaId;

    private LongFilter subcontratistaId;

    private LongFilter clienteId;

    private Boolean distinct;

    public ObraCriteria() {}

    public ObraCriteria(ObraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.provinciaId = other.provinciaId == null ? null : other.provinciaId.copy();
        this.subcontratistaId = other.subcontratistaId == null ? null : other.subcontratistaId.copy();
        this.clienteId = other.clienteId == null ? null : other.clienteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ObraCriteria copy() {
        return new ObraCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getComments() {
        return comments;
    }

    public StringFilter comments() {
        if (comments == null) {
            comments = new StringFilter();
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public LongFilter getProvinciaId() {
        return provinciaId;
    }

    public LongFilter provinciaId() {
        if (provinciaId == null) {
            provinciaId = new LongFilter();
        }
        return provinciaId;
    }

    public void setProvinciaId(LongFilter provinciaId) {
        this.provinciaId = provinciaId;
    }

    public LongFilter getSubcontratistaId() {
        return subcontratistaId;
    }

    public LongFilter subcontratistaId() {
        if (subcontratistaId == null) {
            subcontratistaId = new LongFilter();
        }
        return subcontratistaId;
    }

    public void setSubcontratistaId(LongFilter subcontratistaId) {
        this.subcontratistaId = subcontratistaId;
    }

    public LongFilter getClienteId() {
        return clienteId;
    }

    public LongFilter clienteId() {
        if (clienteId == null) {
            clienteId = new LongFilter();
        }
        return clienteId;
    }

    public void setClienteId(LongFilter clienteId) {
        this.clienteId = clienteId;
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
        final ObraCriteria that = (ObraCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(city, that.city) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(provinciaId, that.provinciaId) &&
            Objects.equals(subcontratistaId, that.subcontratistaId) &&
            Objects.equals(clienteId, that.clienteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, city, comments, provinciaId, subcontratistaId, clienteId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObraCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (provinciaId != null ? "provinciaId=" + provinciaId + ", " : "") +
            (subcontratistaId != null ? "subcontratistaId=" + subcontratistaId + ", " : "") +
            (clienteId != null ? "clienteId=" + clienteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
