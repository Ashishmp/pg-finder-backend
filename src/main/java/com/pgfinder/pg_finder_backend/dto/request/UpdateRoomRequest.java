package com.pgfinder.pg_finder_backend.dto.request;

public class UpdateRoomRequest {
    private Integer rent;
    private Integer totalBeds;
    private Boolean ac;
    private Integer sharingType;


    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(Integer totalBeds) {
        this.totalBeds = totalBeds;
    }

    public Boolean getAc() {
        return ac;
    }

    public void setAc(Boolean ac) {
        this.ac = ac;
    }

    public Integer getSharingType() {
        return sharingType;
    }

    public void setSharingType(Integer sharingType) {
        this.sharingType = sharingType;
    }
}
