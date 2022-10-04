package com.ojeda.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "taxpayer_id")
    private String taxpayerID;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @ManyToOne
    private Provincia provincia;

    @ManyToMany(mappedBy = "clientes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "provincia", "subcontratistas", "clientes" }, allowSetters = true)
    private Set<Obra> obras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Cliente lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Cliente firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTaxpayerID() {
        return this.taxpayerID;
    }

    public Cliente taxpayerID(String taxpayerID) {
        this.setTaxpayerID(taxpayerID);
        return this;
    }

    public void setTaxpayerID(String taxpayerID) {
        this.taxpayerID = taxpayerID;
    }

    public String getAddress() {
        return this.address;
    }

    public Cliente address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public Cliente city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Provincia getProvincia() {
        return this.provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Cliente provincia(Provincia provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.removeCliente(this));
        }
        if (obras != null) {
            obras.forEach(i -> i.addCliente(this));
        }
        this.obras = obras;
    }

    public Cliente obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Cliente addObra(Obra obra) {
        this.obras.add(obra);
        obra.getClientes().add(this);
        return this;
    }

    public Cliente removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.getClientes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", taxpayerID='" + getTaxpayerID() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
