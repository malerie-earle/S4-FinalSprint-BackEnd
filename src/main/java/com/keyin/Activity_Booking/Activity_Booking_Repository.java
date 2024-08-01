package com.keyin.Activity_Booking;

import com.keyin.Activities.Activity;
import com.keyin.Users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Activity_Booking_Repository extends CrudRepository<Activity_Booking,Long> {
    int countByActivityAndDate(Activity activity, LocalDate date);

    List<Activity_Booking> findAllByUser(User user);

    List<Activity_Booking> findAllByActivity(Activity activity);

}


