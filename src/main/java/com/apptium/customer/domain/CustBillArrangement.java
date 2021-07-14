package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A CustBillArrangement.
 */
@Entity
@Table(name = "cust_bill_arrangement")
public class CustBillArrangement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_arrangement_id")
    private Long billArrangementId;

    @Column(name = "statement_acc_no")
    private Long statementAccNo;

    @Column(name = "bill_acc_no")
    private Long billAccNo;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "cycle_date")
    private LocalDate cycleDate;

    @Column(name = "promise_to_pay_date")
    private LocalDate promiseToPayDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "status_date")
    private LocalDate statusDate;

    @Column(name = "status_reason")
    private String statusReason;

    @Column(name = "statement_format_id")
    private String statementFormatId;

    @JsonIgnoreProperties(value = { "custBillArrangement" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private AutoPay autoPay;

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
    @OneToOne(mappedBy = "custBillArrangement")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustBillArrangement id(Long id) {
        this.id = id;
        return this;
    }

    public Long getBillArrangementId() {
        return this.billArrangementId;
    }

    public CustBillArrangement billArrangementId(Long billArrangementId) {
        this.billArrangementId = billArrangementId;
        return this;
    }

    public void setBillArrangementId(Long billArrangementId) {
        this.billArrangementId = billArrangementId;
    }

    public Long getStatementAccNo() {
        return this.statementAccNo;
    }

    public CustBillArrangement statementAccNo(Long statementAccNo) {
        this.statementAccNo = statementAccNo;
        return this;
    }

    public void setStatementAccNo(Long statementAccNo) {
        this.statementAccNo = statementAccNo;
    }

    public Long getBillAccNo() {
        return this.billAccNo;
    }

    public CustBillArrangement billAccNo(Long billAccNo) {
        this.billAccNo = billAccNo;
        return this;
    }

    public void setBillAccNo(Long billAccNo) {
        this.billAccNo = billAccNo;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public CustBillArrangement dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCycleDate() {
        return this.cycleDate;
    }

    public CustBillArrangement cycleDate(LocalDate cycleDate) {
        this.cycleDate = cycleDate;
        return this;
    }

    public void setCycleDate(LocalDate cycleDate) {
        this.cycleDate = cycleDate;
    }

    public LocalDate getPromiseToPayDate() {
        return this.promiseToPayDate;
    }

    public CustBillArrangement promiseToPayDate(LocalDate promiseToPayDate) {
        this.promiseToPayDate = promiseToPayDate;
        return this;
    }

    public void setPromiseToPayDate(LocalDate promiseToPayDate) {
        this.promiseToPayDate = promiseToPayDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public CustBillArrangement startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return this.status;
    }

    public CustBillArrangement status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CustBillArrangement createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getStatusDate() {
        return this.statusDate;
    }

    public CustBillArrangement statusDate(LocalDate statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public void setStatusDate(LocalDate statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusReason() {
        return this.statusReason;
    }

    public CustBillArrangement statusReason(String statusReason) {
        this.statusReason = statusReason;
        return this;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getStatementFormatId() {
        return this.statementFormatId;
    }

    public CustBillArrangement statementFormatId(String statementFormatId) {
        this.statementFormatId = statementFormatId;
        return this;
    }

    public void setStatementFormatId(String statementFormatId) {
        this.statementFormatId = statementFormatId;
    }

    public AutoPay getAutoPay() {
        return this.autoPay;
    }

    public CustBillArrangement autoPay(AutoPay autoPay) {
        this.setAutoPay(autoPay);
        return this;
    }

    public void setAutoPay(AutoPay autoPay) {
        this.autoPay = autoPay;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustBillArrangement customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setCustBillArrangement(null);
        }
        if (customer != null) {
            customer.setCustBillArrangement(this);
        }
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustBillArrangement)) {
            return false;
        }
        return id != null && id.equals(((CustBillArrangement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillArrangement{" +
            "id=" + getId() +
            ", billArrangementId=" + getBillArrangementId() +
            ", statementAccNo=" + getStatementAccNo() +
            ", billAccNo=" + getBillAccNo() +
            ", dueDate='" + getDueDate() + "'" +
            ", cycleDate='" + getCycleDate() + "'" +
            ", promiseToPayDate='" + getPromiseToPayDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", statusReason='" + getStatusReason() + "'" +
            ", statementFormatId='" + getStatementFormatId() + "'" +
            "}";
    }
}
