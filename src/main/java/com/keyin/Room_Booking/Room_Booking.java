package com.keyin.Room_Booking;

import com.keyin.Rooms.Room;
import com.keyin.Users.User;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Room_Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long room_booking_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private Date start_date;
    private Date end_date;

    public long getRoom_booking_id() {
        return room_booking_id;
    }

    public void setRoom_booking_id(long room_booking_id) {
        this.room_booking_id = room_booking_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
