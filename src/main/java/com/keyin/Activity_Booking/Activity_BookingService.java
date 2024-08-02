package com.keyin.Activity_Booking;
import com.keyin.Activities.Activity;
import com.keyin.Activities.ActivityRepository;
import com.keyin.Users.User;
import com.keyin.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Activity_BookingService {

    @Autowired
    private Activity_BookingRepository activityBookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public Activity_Booking createActivityBooking(Long user_id, Long activity_id, LocalDate date) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Activity> activity = activityRepository.findById(activity_id);
        if (user.isPresent() && activity.isPresent()) {
            Activity activityToBook = activity.get();
            int bookedSpots = activityBookingRepository.countByActivityAndDate(activityToBook, date);
            if (bookedSpots < activityToBook.getSpots()) {
                Activity_Booking newBooking = new Activity_Booking();
                newBooking.setUser(user.get());
                newBooking.setActivity(activityToBook);
                newBooking.setDate(date);
                return activityBookingRepository.save(newBooking);
            }
        }
        return null;
    }

    public Activity_Booking getActivityBookingById(long activity_booking_id) {
        Optional<Activity_Booking> result = activityBookingRepository.findById(activity_booking_id);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public List<Activity_Booking> getActivityBookingsByUserId(long user_id) {
        Optional<User> result = userRepository.findById(user_id);
        if (result.isPresent()) {
            return activityBookingRepository.findAllByUser(result.get());
        }
        return null;
    }

    public List<Activity_Booking> getActivityBookingsByActivityId(long activity_id) {
        Optional<Activity> result = activityRepository.findById(activity_id);
        if (result.isPresent()) {
            return activityBookingRepository.findAllByActivity(result.get());
        }
        return null;
    }

    public List<Activity> checkAllActivityAvailability(LocalDate date) {
        List<Activity> allActivities = activityRepository.findAll();
        List<Activity> availableActivities = new ArrayList<>();
        for (Activity activity : allActivities) {
            int totalSpots = activity.getSpots();
            int bookedSpots = activityBookingRepository.countByActivityAndDate(activity, date);
            if (bookedSpots < totalSpots) {
                availableActivities.add(activity);
            }
        }
        return availableActivities;
    }

    public Activity checkAvailabilityByActivityName(String name, LocalDate date) {
        Optional<Activity> activity = activityRepository.findByName(name);
        if (activity.isEmpty()) {
            return null;
        }
        Activity activityToCheck = activity.get();
        int totalSpots = activityToCheck.getSpots();
        int bookedSpots = activityBookingRepository.countByActivityAndDate(activityToCheck, date);
        if (bookedSpots < totalSpots) {
            return activityToCheck;
        } else {
            return null;
        }
    }
}


