package com.pgfinder.pg_finder_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class PgPublicDetailResponse {
    private Long pgId;

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

    private String pgName;
    private String pgAddress;
    private String pgCity;
    private String pgState;
    private String pgCountry;
    private String pgPostalCode;
    private String description;

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public Long getPgId() {
        return pgId;
    }
}
