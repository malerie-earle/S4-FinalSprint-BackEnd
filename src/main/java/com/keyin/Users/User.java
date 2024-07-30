package com.keyin.Users;

import com.keyin.Activity_Booking.Activity_Booking;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1, initialValue=1)
    @GeneratedValue(generator = "user_sequence")
    private Long user_id;

    private String fName;
    private String lName;

    @OneToMany(mappedBy = "user")
    private Set<Activity_Booking> bookings;

    public Long getId() {
        return user_id;
    }

    public void setId(Long user_id) {
        this.user_id = user_id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Set<Activity_Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Activity_Booking> bookings) {
        this.bookings = bookings;
    }
}
