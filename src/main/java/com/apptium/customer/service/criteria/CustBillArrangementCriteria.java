package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.CustBillArrangement} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustBillArrangementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-bill-arrangements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustBillArrangementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter billArrangementId;

    private LongFilter statementAccNo;

    private LongFilter billAccNo;

    private LocalDateFilter dueDate;

    private LocalDateFilter cycleDate;

    private LocalDateFilter promiseToPayDate;

    private LocalDateFilter startDate;

    private StringFilter status;

    private InstantFilter createdDate;

    private LocalDateFilter statusDate;

    private StringFilter statusReason;

    private StringFilter statementFormatId;

    private LongFilter autoPayId;

    private LongFilter customerId;

    public CustBillArrangementCriteria() {}

    public CustBillArrangementCriteria(CustBillArrangementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.billArrangementId = other.billArrangementId == null ? null : other.billArrangementId.copy();
        this.statementAccNo = other.statementAccNo == null ? null : other.statementAccNo.copy();
        this.billAccNo = other.billAccNo == null ? null : other.billAccNo.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.cycleDate = other.cycleDate == null ? null : other.cycleDate.copy();
        this.promiseToPayDate = other.promiseToPayDate == null ? null : other.promiseToPayDate.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.statusDate = other.statusDate == null ? null : other.statusDate.copy();
        this.statusReason = other.statusReason == null ? null : other.statusReason.copy();
        this.statementFormatId = other.statementFormatId == null ? null : other.statementFormatId.copy();
        this.autoPayId = other.autoPayId == null ? null : other.autoPayId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CustBillArrangementCriteria copy() {
        return new CustBillArrangementCriteria(this);
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

    public LongFilter getBillArrangementId() {
        return billArrangementId;
    }

    public LongFilter billArrangementId() {
        if (billArrangementId == null) {
            billArrangementId = new LongFilter();
        }
        return billArrangementId;
    }

    public void setBillArrangementId(LongFilter billArrangementId) {
        this.billArrangementId = billArrangementId;
    }

    public LongFilter getStatementAccNo() {
        return statementAccNo;
    }

    public LongFilter statementAccNo() {
        if (statementAccNo == null) {
            statementAccNo = new LongFilter();
        }
        return statementAccNo;
    }

    public void setStatementAccNo(LongFilter statementAccNo) {
        this.statementAccNo = statementAccNo;
    }

    public LongFilter getBillAccNo() {
        return billAccNo;
    }

    public LongFilter billAccNo() {
        if (billAccNo == null) {
            billAccNo = new LongFilter();
        }
        return billAccNo;
    }

    public void setBillAccNo(LongFilter billAccNo) {
        this.billAccNo = billAccNo;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public LocalDateFilter dueDate() {
        if (dueDate == null) {
            dueDate = new LocalDateFilter();
        }
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateFilter getCycleDate() {
        return cycleDate;
    }

    public LocalDateFilter cycleDate() {
        if (cycleDate == null) {
            cycleDate = new LocalDateFilter();
        }
        return cycleDate;
    }

    public void setCycleDate(LocalDateFilter cycleDate) {
        this.cycleDate = cycleDate;
    }

    public LocalDateFilter getPromiseToPayDate() {
        return promiseToPayDate;
    }

    public LocalDateFilter promiseToPayDate() {
        if (promiseToPayDate == null) {
            promiseToPayDate = new LocalDateFilter();
        }
        return promiseToPayDate;
    }

    public void setPromiseToPayDate(LocalDateFilter promiseToPayDate) {
        this.promiseToPayDate = promiseToPayDate;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            startDate = new LocalDateFilter();
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateFilter getStatusDate() {
        return statusDate;
    }

    public LocalDateFilter statusDate() {
        if (statusDate == null) {
            statusDate = new LocalDateFilter();
        }
        return statusDate;
    }

    public void setStatusDate(LocalDateFilter statusDate) {
        this.statusDate = statusDate;
    }

    public StringFilter getStatusReason() {
        return statusReason;
    }

    public StringFilter statusReason() {
        if (statusReason == null) {
            statusReason = new StringFilter();
        }
        return statusReason;
    }

    public void setStatusReason(StringFilter statusReason) {
        this.statusReason = statusReason;
    }

    public StringFilter getStatementFormatId() {
        return statementFormatId;
    }

    public StringFilter statementFormatId() {
        if (statementFormatId == null) {
            statementFormatId = new StringFilter();
        }
        return statementFormatId;
    }

    public void setStatementFormatId(StringFilter statementFormatId) {
        this.statementFormatId = statementFormatId;
    }

    public LongFilter getAutoPayId() {
        return autoPayId;
    }

    public LongFilter autoPayId() {
        if (autoPayId == null) {
            autoPayId = new LongFilter();
        }
        return autoPayId;
    }

    public void setAutoPayId(LongFilter autoPayId) {
        this.autoPayId = autoPayId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public LongFilter customerId() {
        if (customerId == null) {
            customerId = new LongFilter();
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustBillArrangementCriteria that = (CustBillArrangementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(billArrangementId, that.billArrangementId) &&
            Objects.equals(statementAccNo, that.statementAccNo) &&
            Objects.equals(billAccNo, that.billAccNo) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(cycleDate, that.cycleDate) &&
            Objects.equals(promiseToPayDate, that.promiseToPayDate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(statusDate, that.statusDate) &&
            Objects.equals(statusReason, that.statusReason) &&
            Objects.equals(statementFormatId, that.statementFormatId) &&
            Objects.equals(autoPayId, that.autoPayId) &&
            Objects.equals(customerId, that.customerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            billArrangementId,
            statementAccNo,
            billAccNo,
            dueDate,
            cycleDate,
            promiseToPayDate,
            startDate,
            status,
            createdDate,
            statusDate,
            statusReason,
            statementFormatId,
            autoPayId,
            customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillArrangementCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (billArrangementId != null ? "billArrangementId=" + billArrangementId + ", " : "") +
            (statementAccNo != null ? "statementAccNo=" + statementAccNo + ", " : "") +
            (billAccNo != null ? "billAccNo=" + billAccNo + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (cycleDate != null ? "cycleDate=" + cycleDate + ", " : "") +
            (promiseToPayDate != null ? "promiseToPayDate=" + promiseToPayDate + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (statusDate != null ? "statusDate=" + statusDate + ", " : "") +
            (statusReason != null ? "statusReason=" + statusReason + ", " : "") +
            (statementFormatId != null ? "statementFormatId=" + statementFormatId + ", " : "") +
            (autoPayId != null ? "autoPayId=" + autoPayId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
