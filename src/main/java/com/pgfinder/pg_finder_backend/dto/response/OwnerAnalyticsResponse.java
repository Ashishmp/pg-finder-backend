package com.pgfinder.pg_finder_backend.dto.response;

public class OwnerAnalyticsResponse {

    private long totalPgs;
    private long totalRooms;
    private long totalBeds;
    private long occupiedBeds;
    private double occupancyRate;
    private double monthlyRevenue;


    public long getTotalPgs() {
        return totalPgs;
    }

    public void setTotalPgs(long totalPgs) {
        this.totalPgs = totalPgs;
    }

    public long getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(long totalRooms) {
        this.totalRooms = totalRooms;
    }

    public long getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(long totalBeds) {
        this.totalBeds = totalBeds;
    }

    public long getOccupiedBeds() {
        return occupiedBeds;
    }

    public void setOccupiedBeds(long occupiedBeds) {
        this.occupiedBeds = occupiedBeds;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(double occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public double getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(double monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }
}
