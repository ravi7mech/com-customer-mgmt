package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustRelParty.
 */
@Entity
@Table(name = "cust_rel_party")
public class CustRelParty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "indActivation", "indNewsLetterConf", "indAuditTrials", "indChars", "indContacts", "shoppingSessionRef", "custRelParties",
        },
        allowSetters = true
    )
    private Individual individual;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "custRelParties" }, allowSetters = true)
    private RoleTypeRef roleTypeRef;

    @ManyToOne
    @JsonIgnoreProperties(value = { "custRelParties" }, allowSetters = true)
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustRelParty id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustRelParty name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public CustRelParty roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Instant getValidFrom() {
        return this.validFrom;
    }

    public CustRelParty validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return this.validTo;
    }

    public CustRelParty validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public CustRelParty individual(Individual individual) {
        this.setIndividual(individual);
        return this;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustRelParty customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RoleTypeRef getRoleTypeRef() {
        return this.roleTypeRef;
    }

    public CustRelParty roleTypeRef(RoleTypeRef roleTypeRef) {
        this.setRoleTypeRef(roleTypeRef);
        return this;
    }

    public void setRoleTypeRef(RoleTypeRef roleTypeRef) {
        this.roleTypeRef = roleTypeRef;
    }

    public Department getDepartment() {
        return this.department;
    }

    public CustRelParty department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustRelParty)) {
            return false;
        }
        return id != null && id.equals(((CustRelParty) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustRelParty{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", roleId=" + getRoleId() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
