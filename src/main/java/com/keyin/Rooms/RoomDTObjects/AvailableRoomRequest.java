package com.keyin.Rooms.RoomDTObjects;

import java.util.Date;

public class AvailableRoomRequest{
    private Date startDate;
    private Date endDate;
    private int occupancy;
    private String type;
    public AvailableRoomRequest(){
    }

    public AvailableRoomRequest(Date startDate, Date endDate, int occupancy, String type){
        this.startDate = startDate;
        this.endDate = endDate;
        this.occupancy = occupancy;
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
