package com.pgfinder.pg_finder_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class CreatePgRequest {

    @NotBlank
    private String pgName;
    @NotBlank
    private String pgAddress;
    @NotBlank
    private String pgCity;
    @NotBlank
    private String pgState;
    @NotBlank
    private String pgCountry;
    @NotBlank
    @Size(min = 6, max = 6)
    private String pgPostalCode;
    @NotBlank
    @Length(max = 500)
    private String description;

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
}
