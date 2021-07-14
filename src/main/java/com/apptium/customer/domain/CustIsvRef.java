package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustIsvRef.
 */
@Entity
@Table(name = "cust_isv_ref")
public class CustIsvRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "isv_id", nullable = false)
    private Integer isvId;

    @NotNull
    @Column(name = "isv_name", nullable = false)
    private String isvName;

    @NotNull
    @Column(name = "isv_cust_id", nullable = false)
    private Long isvCustId;

    @OneToMany(mappedBy = "custIsvRef")
    @JsonIgnoreProperties(value = { "custIsvRef" }, allowSetters = true)
    private Set<CustIsvChar> custIsvChars = new HashSet<>();

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

    public CustIsvRef id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getIsvId() {
        return this.isvId;
    }

    public CustIsvRef isvId(Integer isvId) {
        this.isvId = isvId;
        return this;
    }

    public void setIsvId(Integer isvId) {
        this.isvId = isvId;
    }

    public String getIsvName() {
        return this.isvName;
    }

    public CustIsvRef isvName(String isvName) {
        this.isvName = isvName;
        return this;
    }

    public void setIsvName(String isvName) {
        this.isvName = isvName;
    }

    public Long getIsvCustId() {
        return this.isvCustId;
    }

    public CustIsvRef isvCustId(Long isvCustId) {
        this.isvCustId = isvCustId;
        return this;
    }

    public void setIsvCustId(Long isvCustId) {
        this.isvCustId = isvCustId;
    }

    public Set<CustIsvChar> getCustIsvChars() {
        return this.custIsvChars;
    }

    public CustIsvRef custIsvChars(Set<CustIsvChar> custIsvChars) {
        this.setCustIsvChars(custIsvChars);
        return this;
    }

    public CustIsvRef addCustIsvChar(CustIsvChar custIsvChar) {
        this.custIsvChars.add(custIsvChar);
        custIsvChar.setCustIsvRef(this);
        return this;
    }

    public CustIsvRef removeCustIsvChar(CustIsvChar custIsvChar) {
        this.custIsvChars.remove(custIsvChar);
        custIsvChar.setCustIsvRef(null);
        return this;
    }

    public void setCustIsvChars(Set<CustIsvChar> custIsvChars) {
        if (this.custIsvChars != null) {
            this.custIsvChars.forEach(i -> i.setCustIsvRef(null));
        }
        if (custIsvChars != null) {
            custIsvChars.forEach(i -> i.setCustIsvRef(this));
        }
        this.custIsvChars = custIsvChars;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustIsvRef customer(Customer customer) {
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
        if (!(o instanceof CustIsvRef)) {
            return false;
        }
        return id != null && id.equals(((CustIsvRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustIsvRef{" +
            "id=" + getId() +
            ", isvId=" + getIsvId() +
            ", isvName='" + getIsvName() + "'" +
            ", isvCustId=" + getIsvCustId() +
            "}";
    }
}
