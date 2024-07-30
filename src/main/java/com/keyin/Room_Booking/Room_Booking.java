package com.keyin.Room_Booking;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Room_Booking {
    @Id
    @SequenceGenerator(name = "greeting_sequence", sequenceName = "greeting_sequence", allocationSize = 1, initialValue=1)
    @GeneratedValue(generator = "greeting_sequence")
    private long room_booking_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private long user_id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private long room_id;

    private Date start_date;
    private Date end_date;

    public long getRoom_booking_id() {
        return room_booking_id;
    }

    public void setRoom_booking_id(long room_booking_id) {
        this.room_booking_id = room_booking_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
