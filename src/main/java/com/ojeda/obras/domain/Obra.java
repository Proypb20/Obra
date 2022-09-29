package com.ojeda.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Obra.
 */
@Entity
@Table(name = "obra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Obra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    private Provincia provincia;

    @ManyToMany(mappedBy = "obras")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "obras" }, allowSetters = true)
    private Set<Subcontratista> subcontratistas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Obra id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Obra name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Obra address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public Obra city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Obra comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public Provincia getProvincia() {
        return this.provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Obra provincia(Provincia provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public Set<Subcontratista> getSubcontratistas() {
        return this.subcontratistas;
    }

    public void setSubcontratistas(Set<Subcontratista> subcontratistas) {
        if (this.subcontratistas != null) {
            this.subcontratistas.forEach(i -> i.removeObra(this));
        }
        if (subcontratistas != null) {
            subcontratistas.forEach(i -> i.addObra(this));
        }
        this.subcontratistas = subcontratistas;
    }

    public Obra subcontratistas(Set<Subcontratista> subcontratistas) {
        this.setSubcontratistas(subcontratistas);
        return this;
    }

    public Obra addSubcontratista(Subcontratista subcontratista) {
        this.subcontratistas.add(subcontratista);
        subcontratista.getObras().add(this);
        return this;
    }

    public Obra removeSubcontratista(Subcontratista subcontratista) {
        this.subcontratistas.remove(subcontratista);
        subcontratista.getObras().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Obra)) {
            return false;
        }
        return id != null && id.equals(((Obra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Obra{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
