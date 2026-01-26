package com.pgfinder.pg_finder_backend.dto.response;

public class RoomResponse {
    private Long roomId;
    private Double rent;
    private Long pgId;
    private Integer sharingType;
    private Boolean ac;
    private Integer totalBeds;
    private Integer availableBeds;
    private String status;
//    private PgMiniResponse pg;


    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Integer getSharingType() {
        return sharingType;
    }

    public void setSharingType(Integer sharingType) {
        this.sharingType = sharingType;
    }

    public Boolean getAc() {
        return ac;
    }

    public void setAc(Boolean ac) {
        this.ac = ac;
    }

    public Integer getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(Integer totalBeds) {
        this.totalBeds = totalBeds;
    }

    public Integer getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(Integer availableBeds) {
        this.availableBeds = availableBeds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public PgMiniResponse getPg() {
//        return pg;
//    }
//
//    public void setPg(PgMiniResponse pg) {
//        this.pg = pg;
//    }
}

