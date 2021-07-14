package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustIsvRef} entity.
 */
public class CustIsvRefDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer isvId;

    @NotNull
    private String isvName;

    @NotNull
    private Long isvCustId;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsvId() {
        return isvId;
    }

    public void setIsvId(Integer isvId) {
        this.isvId = isvId;
    }

    public String getIsvName() {
        return isvName;
    }

    public void setIsvName(String isvName) {
        this.isvName = isvName;
    }

    public Long getIsvCustId() {
        return isvCustId;
    }

    public void setIsvCustId(Long isvCustId) {
        this.isvCustId = isvCustId;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustIsvRefDTO)) {
            return false;
        }

        CustIsvRefDTO custIsvRefDTO = (CustIsvRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custIsvRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustIsvRefDTO{" +
            "id=" + getId() +
            ", isvId=" + getIsvId() +
            ", isvName='" + getIsvName() + "'" +
            ", isvCustId=" + getIsvCustId() +
            ", customer=" + getCustomer() +
            "}";
    }
}
