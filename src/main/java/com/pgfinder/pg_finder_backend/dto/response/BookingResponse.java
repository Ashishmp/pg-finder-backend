package com.pgfinder.pg_finder_backend.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {

    private Long bookingId;
    private String status;
    private String startDate;
    private String endDate;
    private Double price;

    private Long roomId;
    private Integer sharingType;
    private Boolean ac;

    private Long pgId;
    private String pgName;
    private String pgCity;

    // getters & setters

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgCity() {
        return pgCity;
    }

    public void setPgCity(String pgCity) {
        this.pgCity = pgCity;
    }
}
