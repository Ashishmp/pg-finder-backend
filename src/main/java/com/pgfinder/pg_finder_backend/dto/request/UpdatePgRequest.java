package com.pgfinder.pg_finder_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePgRequest {

    @NotBlank(message = "PG name is required")
    private String pgName;

    @NotBlank(message = "PG address is required")
    private String pgAddress;

    @NotBlank(message = "City is required")
    private String pgCity;

    @NotBlank(message = "State is required")
    private String pgState;

    @NotBlank(message = "Country is required")
    private String pgCountry;

    @NotBlank(message = "Postal code is required")
    private String pgPostalCode;

    @Size(max = 500, message = "Description must be under 500 characters")
    private String description;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    // ---- Getters & Setters ----

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgAddress() {
        return pgAddress;
    }

    public void setPgAddress(String pgAddress) {
        this.pgAddress = pgAddress;
    }

    public String getPgCity() {
        return pgCity;
    }

    public void setPgCity(String pgCity) {
        this.pgCity = pgCity;
    }

    public String getPgState() {
        return pgState;
    }

    public void setPgState(String pgState) {
        this.pgState = pgState;
    }

    public String getPgCountry() {
        return pgCountry;
    }

    public void setPgCountry(String pgCountry) {
        this.pgCountry = pgCountry;
    }

    public String getPgPostalCode() {
        return pgPostalCode;
    }

    public void setPgPostalCode(String pgPostalCode) {
        this.pgPostalCode = pgPostalCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
