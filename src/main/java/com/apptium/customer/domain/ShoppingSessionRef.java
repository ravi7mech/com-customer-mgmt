package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ShoppingSessionRef.
 */
@Entity
@Table(name = "shopping_session_ref")
public class ShoppingSessionRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "href")
    private String href;

    @NotNull
    @Column(name = "shopping_session_id", nullable = false)
    private String shoppingSessionId;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "session_abondoned", nullable = false)
    private Boolean sessionAbondoned;

    @NotNull
    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "user_id")
    private Long userId;

    @JsonIgnoreProperties(
        value = {
            "indActivation", "indNewsLetterConf", "indAuditTrials", "indChars", "indContacts", "shoppingSessionRef", "custRelParties",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShoppingSessionRef id(Long id) {
        this.id = id;
        return this;
    }

    public String getHref() {
        return this.href;
    }

    public ShoppingSessionRef href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getShoppingSessionId() {
        return this.shoppingSessionId;
    }

    public ShoppingSessionRef shoppingSessionId(String shoppingSessionId) {
        this.shoppingSessionId = shoppingSessionId;
        return this;
    }

    public void setShoppingSessionId(String shoppingSessionId) {
        this.shoppingSessionId = shoppingSessionId;
    }

    public String getStatus() {
        return this.status;
    }

    public ShoppingSessionRef status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSessionAbondoned() {
        return this.sessionAbondoned;
    }

    public ShoppingSessionRef sessionAbondoned(Boolean sessionAbondoned) {
        this.sessionAbondoned = sessionAbondoned;
        return this;
    }

    public void setSessionAbondoned(Boolean sessionAbondoned) {
        this.sessionAbondoned = sessionAbondoned;
    }

    public String getChannel() {
        return this.channel;
    }

    public ShoppingSessionRef channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getUserId() {
        return this.userId;
    }

    public ShoppingSessionRef userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public ShoppingSessionRef individual(Individual individual) {
        this.setIndividual(individual);
        return this;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public ShoppingSessionRef customer(Customer customer) {
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
        if (!(o instanceof ShoppingSessionRef)) {
            return false;
        }
        return id != null && id.equals(((ShoppingSessionRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingSessionRef{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", shoppingSessionId='" + getShoppingSessionId() + "'" +
            ", status='" + getStatus() + "'" +
            ", sessionAbondoned='" + getSessionAbondoned() + "'" +
            ", channel='" + getChannel() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
