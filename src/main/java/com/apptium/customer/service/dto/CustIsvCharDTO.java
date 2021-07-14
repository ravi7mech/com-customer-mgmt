package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustIsvChar} entity.
 */
public class CustIsvCharDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer value;

    @NotNull
    private String valueType;

    private CustIsvRefDTO custIsvRef;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public CustIsvRefDTO getCustIsvRef() {
        return custIsvRef;
    }

    public void setCustIsvRef(CustIsvRefDTO custIsvRef) {
        this.custIsvRef = custIsvRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustIsvCharDTO)) {
            return false;
        }

        CustIsvCharDTO custIsvCharDTO = (CustIsvCharDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custIsvCharDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustIsvCharDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            ", valueType='" + getValueType() + "'" +
            ", custIsvRef=" + getCustIsvRef() +
            "}";
    }
}
