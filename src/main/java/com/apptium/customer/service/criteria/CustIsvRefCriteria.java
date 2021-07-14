package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.CustIsvRef} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustIsvRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-isv-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustIsvRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter isvId;

    private StringFilter isvName;

    private LongFilter isvCustId;

    private LongFilter custIsvCharId;

    private LongFilter customerId;

    public CustIsvRefCriteria() {}

    public CustIsvRefCriteria(CustIsvRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isvId = other.isvId == null ? null : other.isvId.copy();
        this.isvName = other.isvName == null ? null : other.isvName.copy();
        this.isvCustId = other.isvCustId == null ? null : other.isvCustId.copy();
        this.custIsvCharId = other.custIsvCharId == null ? null : other.custIsvCharId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CustIsvRefCriteria copy() {
        return new CustIsvRefCriteria(this);
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

    public IntegerFilter getIsvId() {
        return isvId;
    }

    public IntegerFilter isvId() {
        if (isvId == null) {
            isvId = new IntegerFilter();
        }
        return isvId;
    }

    public void setIsvId(IntegerFilter isvId) {
        this.isvId = isvId;
    }

    public StringFilter getIsvName() {
        return isvName;
    }

    public StringFilter isvName() {
        if (isvName == null) {
            isvName = new StringFilter();
        }
        return isvName;
    }

    public void setIsvName(StringFilter isvName) {
        this.isvName = isvName;
    }

    public LongFilter getIsvCustId() {
        return isvCustId;
    }

    public LongFilter isvCustId() {
        if (isvCustId == null) {
            isvCustId = new LongFilter();
        }
        return isvCustId;
    }

    public void setIsvCustId(LongFilter isvCustId) {
        this.isvCustId = isvCustId;
    }

    public LongFilter getCustIsvCharId() {
        return custIsvCharId;
    }

    public LongFilter custIsvCharId() {
        if (custIsvCharId == null) {
            custIsvCharId = new LongFilter();
        }
        return custIsvCharId;
    }

    public void setCustIsvCharId(LongFilter custIsvCharId) {
        this.custIsvCharId = custIsvCharId;
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
        final CustIsvRefCriteria that = (CustIsvRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isvId, that.isvId) &&
            Objects.equals(isvName, that.isvName) &&
            Objects.equals(isvCustId, that.isvCustId) &&
            Objects.equals(custIsvCharId, that.custIsvCharId) &&
            Objects.equals(customerId, that.customerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isvId, isvName, isvCustId, custIsvCharId, customerId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustIsvRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isvId != null ? "isvId=" + isvId + ", " : "") +
            (isvName != null ? "isvName=" + isvName + ", " : "") +
            (isvCustId != null ? "isvCustId=" + isvCustId + ", " : "") +
            (custIsvCharId != null ? "custIsvCharId=" + custIsvCharId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
