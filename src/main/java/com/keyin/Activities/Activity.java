package com.keyin.Activities;

import com.keyin.Activity_Booking.Activity_Booking;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class Activity {

    @Id
    @SequenceGenerator(name = "activity_sequence", sequenceName = "activity_sequence", allocationSize = 1, initialValue=1)
    @GeneratedValue(generator = "activity_sequence")
    private Long activity_id;

    private String name;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String description;
    private String image;
    private int spots;

    @OneToMany(mappedBy = "activity")
    private Set<Activity_Booking> bookings;


    public Long getActivityId() {
        return activity_id;
    }

    public void setActivityId(Long activity_id) {
        this.activity_id = activity_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSpots() {
        return spots;
    }

    public void setSpots(int spots) {
        this.spots = spots;
    }

    public Set<Activity_Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Activity_Booking> bookings) {
        this.bookings = bookings;
    }
}
