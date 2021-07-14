package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustIsvChar.
 */
@Entity
@Table(name = "cust_isv_char")
public class CustIsvChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private Integer value;

    @NotNull
    @Column(name = "value_type", nullable = false)
    private String valueType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "custIsvChars", "customer" }, allowSetters = true)
    private CustIsvRef custIsvRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustIsvChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustIsvChar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return this.value;
    }

    public CustIsvChar value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getValueType() {
        return this.valueType;
    }

    public CustIsvChar valueType(String valueType) {
        this.valueType = valueType;
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public CustIsvRef getCustIsvRef() {
        return this.custIsvRef;
    }

    public CustIsvChar custIsvRef(CustIsvRef custIsvRef) {
        this.setCustIsvRef(custIsvRef);
        return this;
    }

    public void setCustIsvRef(CustIsvRef custIsvRef) {
        this.custIsvRef = custIsvRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustIsvChar)) {
            return false;
        }
        return id != null && id.equals(((CustIsvChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustIsvChar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            ", valueType='" + getValueType() + "'" +
            "}";
    }
}
