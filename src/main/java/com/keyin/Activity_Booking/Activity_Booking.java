package com.keyin.Activity_Booking;
import com.keyin.Activities.Activity;
import com.keyin.Users.User;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
public class Activity_Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activity_booking_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Column(nullable = false)
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
