package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustSecurityChar.
 */
@Entity
@Table(name = "cust_security_char")
public class CustSecurityChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "value_type", nullable = false)
    private String valueType;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "custBillingAcc",
            "custBillArrangement",
            "custContacts",
            "custStatistics",
            "custChars",
            "custSecurityChars",
            "custIsvRefs",
            "custRelParties",
            "custCommunicationRefs",
            "custPasswordChars",
            "custNewsLetterConfigs",
            "custCreditProfiles",
            "custBillingRefs",
            "shoppingSessionRefs",
            "industry",
        },
        allowSetters = true
    )
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustSecurityChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustSecurityChar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public CustSecurityChar value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return this.valueType;
    }

    public CustSecurityChar valueType(String valueType) {
        this.valueType = valueType;
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustSecurityChar customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustSecurityChar)) {
            return false;
        }
        return id != null && id.equals(((CustSecurityChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustSecurityChar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueType='" + getValueType() + "'" +
            "}";
    }
}
