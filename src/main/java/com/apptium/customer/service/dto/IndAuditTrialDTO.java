package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.IndAuditTrial} entity.
 */
public class IndAuditTrialDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String activity;

    private Instant createdDate;

    private IndividualDTO individual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public IndividualDTO getIndividual() {
        return individual;
    }

    public void setIndividual(IndividualDTO individual) {
        this.individual = individual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndAuditTrialDTO)) {
            return false;
        }

        IndAuditTrialDTO indAuditTrialDTO = (IndAuditTrialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, indAuditTrialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndAuditTrialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activity='" + getActivity() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", individual=" + getIndividual() +
            "}";
    }
}
