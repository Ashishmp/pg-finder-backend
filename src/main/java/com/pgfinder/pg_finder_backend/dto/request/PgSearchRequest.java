package com.pgfinder.pg_finder_backend.dto.request;

import java.util.List;

public class PgSearchRequest {
    private String city;
    private Double minRent;
    private Double maxRent;
    private Boolean isAc;
    private Integer sharingType;
    private List<String> amenities;

    // pagination
    private int page = 0;
    private int size = 10;

    // sorting
    private String sortBy = "createdAt";
    private String direction = "DESC";

    // getters only (no setters needed for @RequestParam binding)
    public String getCity() { return city; }
    public Double getMinRent() { return minRent; }
    public Double getMaxRent() { return maxRent; }
    public Boolean getIsAc() { return isAc; }
    public Integer getSharingType() { return sharingType; }
    public List<String> getAmenities() { return amenities; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public String getSortBy() { return sortBy; }
    public String getDirection() { return direction; }
}
