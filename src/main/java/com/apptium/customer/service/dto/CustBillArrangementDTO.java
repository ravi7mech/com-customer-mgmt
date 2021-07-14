package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustBillArrangement} entity.
 */
public class CustBillArrangementDTO implements Serializable {

    private Long id;

    private Long billArrangementId;

    private Long statementAccNo;

    private Long billAccNo;

    private LocalDate dueDate;

    private LocalDate cycleDate;

    private LocalDate promiseToPayDate;

    private LocalDate startDate;

    private String status;

    private Instant createdDate;

    private LocalDate statusDate;

    private String statusReason;

    private String statementFormatId;

    private AutoPayDTO autoPay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillArrangementId() {
        return billArrangementId;
    }

    public void setBillArrangementId(Long billArrangementId) {
        this.billArrangementId = billArrangementId;
    }

    public Long getStatementAccNo() {
        return statementAccNo;
    }

    public void setStatementAccNo(Long statementAccNo) {
        this.statementAccNo = statementAccNo;
    }

    public Long getBillAccNo() {
        return billAccNo;
    }

    public void setBillAccNo(Long billAccNo) {
        this.billAccNo = billAccNo;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCycleDate() {
        return cycleDate;
    }

    public void setCycleDate(LocalDate cycleDate) {
        this.cycleDate = cycleDate;
    }

    public LocalDate getPromiseToPayDate() {
        return promiseToPayDate;
    }

    public void setPromiseToPayDate(LocalDate promiseToPayDate) {
        this.promiseToPayDate = promiseToPayDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDate statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getStatementFormatId() {
        return statementFormatId;
    }

    public void setStatementFormatId(String statementFormatId) {
        this.statementFormatId = statementFormatId;
    }

    public AutoPayDTO getAutoPay() {
        return autoPay;
    }

    public void setAutoPay(AutoPayDTO autoPay) {
        this.autoPay = autoPay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustBillArrangementDTO)) {
            return false;
        }

        CustBillArrangementDTO custBillArrangementDTO = (CustBillArrangementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custBillArrangementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillArrangementDTO{" +
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
            ", autoPay=" + getAutoPay() +
            "}";
    }
}
