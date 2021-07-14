package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.ShoppingSessionRef} entity.
 */
public class ShoppingSessionRefDTO implements Serializable {

    private Long id;

    private String href;

    @NotNull
    private String shoppingSessionId;

    @NotNull
    private String status;

    @NotNull
    private Boolean sessionAbondoned;

    @NotNull
    private String channel;

    private Long userId;

    private IndividualDTO individual;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getShoppingSessionId() {
        return shoppingSessionId;
    }

    public void setShoppingSessionId(String shoppingSessionId) {
        this.shoppingSessionId = shoppingSessionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSessionAbondoned() {
        return sessionAbondoned;
    }

    public void setSessionAbondoned(Boolean sessionAbondoned) {
        this.sessionAbondoned = sessionAbondoned;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public IndividualDTO getIndividual() {
        return individual;
    }

    public void setIndividual(IndividualDTO individual) {
        this.individual = individual;
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
        if (!(o instanceof ShoppingSessionRefDTO)) {
            return false;
        }

        ShoppingSessionRefDTO shoppingSessionRefDTO = (ShoppingSessionRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shoppingSessionRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingSessionRefDTO{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", shoppingSessionId='" + getShoppingSessionId() + "'" +
            ", status='" + getStatus() + "'" +
            ", sessionAbondoned='" + getSessionAbondoned() + "'" +
            ", channel='" + getChannel() + "'" +
            ", userId=" + getUserId() +
            ", individual=" + getIndividual() +
            ", customer=" + getCustomer() +
            "}";
    }
}
