package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.Customer} entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String formattedName;

    @NotNull
    private String tradingName;

    @NotNull
    private String custType;

    @NotNull
    private String title;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String middleName;

    @NotNull
    private LocalDate dateOfBirth;

    private String gender;

    private String maritalStatus;

    @NotNull
    private String nationality;

    @NotNull
    private String status;

    @NotNull
    private String custEmail;

    @NotNull
    private String compIdType;

    @NotNull
    private Long compId;

    @NotNull
    private Long primaryConAdminIndId;

    private CustBillingAccDTO custBillingAcc;

    private CustBillArrangementDTO custBillArrangement;

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

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCompIdType() {
        return compIdType;
    }

    public void setCompIdType(String compIdType) {
        this.compIdType = compIdType;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getPrimaryConAdminIndId() {
        return primaryConAdminIndId;
    }

    public void setPrimaryConAdminIndId(Long primaryConAdminIndId) {
        this.primaryConAdminIndId = primaryConAdminIndId;
    }

    public CustBillingAccDTO getCustBillingAcc() {
        return custBillingAcc;
    }

    public void setCustBillingAcc(CustBillingAccDTO custBillingAcc) {
        this.custBillingAcc = custBillingAcc;
    }

    public CustBillArrangementDTO getCustBillArrangement() {
        return custBillArrangement;
    }

    public void setCustBillArrangement(CustBillArrangementDTO custBillArrangement) {
        this.custBillArrangement = custBillArrangement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", formattedName='" + getFormattedName() + "'" +
            ", tradingName='" + getTradingName() + "'" +
            ", custType='" + getCustType() + "'" +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", status='" + getStatus() + "'" +
            ", custEmail='" + getCustEmail() + "'" +
            ", compIdType='" + getCompIdType() + "'" +
            ", compId=" + getCompId() +
            ", primaryConAdminIndId=" + getPrimaryConAdminIndId() +
            ", custBillingAcc=" + getCustBillingAcc() +
            ", custBillArrangement=" + getCustBillArrangement() +
            "}";
    }
}
