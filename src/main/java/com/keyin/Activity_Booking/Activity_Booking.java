package com.keyin.Activity_Booking;

import com.keyin.Activities.Activity;
import com.keyin.Users.User;
import jakarta.persistence.*;

@Entity
public class Activity_Booking {

    @Id
    @SequenceGenerator(name = "activity_booking_sequence", sequenceName = "activity_booking_sequence", allocationSize = 1, initialValue=1)
    @GeneratedValue(generator = "activity_booking_sequence")
    private Long activity_booking_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    public Long getActivity_booking_id() {
        return activity_booking_id;
    }

    public void setActivity_booking_id(Long activity_booking_id) {
        this.activity_booking_id = activity_booking_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
