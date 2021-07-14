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
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.IndAuditTrial} entity. This class is used
 * in {@link com.apptium.customer.web.rest.IndAuditTrialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ind-audit-trials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IndAuditTrialCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter activity;

    private InstantFilter createdDate;

    private LongFilter individualId;

    public IndAuditTrialCriteria() {}

    public IndAuditTrialCriteria(IndAuditTrialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.activity = other.activity == null ? null : other.activity.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
    }

    @Override
    public IndAuditTrialCriteria copy() {
        return new IndAuditTrialCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getActivity() {
        return activity;
    }

    public StringFilter activity() {
        if (activity == null) {
            activity = new StringFilter();
        }
        return activity;
    }

    public void setActivity(StringFilter activity) {
        this.activity = activity;
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

    public LongFilter getIndividualId() {
        return individualId;
    }

    public LongFilter individualId() {
        if (individualId == null) {
            individualId = new LongFilter();
        }
        return individualId;
    }

    public void setIndividualId(LongFilter individualId) {
        this.individualId = individualId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IndAuditTrialCriteria that = (IndAuditTrialCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(activity, that.activity) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(individualId, that.individualId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, activity, createdDate, individualId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndAuditTrialCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (activity != null ? "activity=" + activity + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            "}";
    }
}
