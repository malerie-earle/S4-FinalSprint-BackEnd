package com.keyin.Activity_Booking;
import com.keyin.Activities.Activity;
import com.keyin.Users.User;
import com.keyin.Users.UserRepository;
import com.keyin.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class Activity_BookingController {

    @Autowired
    private Activity_BookingRepository activityBookingRepository;
    @Autowired
    private Activity_BookingService activityBookingService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/activities/book")
    public Activity_Booking createActivityBooking(@RequestBody Activity_BookingEntryDTO request){

        String userName = request.getUserName();
        List<User> users = userRepository.findAllByUsername(userName);
        System.out.println(request.getUserName());
        System.out.println(users);

        System.out.println(users.get(0));
        System.out.println(users.get(0).getUserId());

        if(!users.isEmpty()){
            Long activity_id = request.getActivityId();
            LocalDate date = request.getDate();
            return activityBookingService.createActivityBooking(users.get(0).getUserId(), activity_id, date);
        }
        return null;
    }

    @GetMapping("/activities/bookings/{activity_booking_id}")
    public Activity_Booking getActivityBookingById(@PathVariable Long activity_booking_id){
        return activityBookingService.getActivityBookingById(activity_booking_id);
    }

    @GetMapping("/activities/bookings/user/{user_id}")
        public List<Activity_Booking> getActivityBookingsByUserId(@PathVariable Long user_id){
        return activityBookingService.getActivityBookingsByUserId(user_id);
    }

    @GetMapping("/activities/bookings/activity/{activity_id}")
    public List<Activity_Booking> getActivityBookingsByActivityId(@PathVariable Long activity_id){
        return activityBookingService.getActivityBookingsByActivityId(activity_id);
    }

    @GetMapping("/activities/availability/all")
    public List<Activity> checkAllActivityAvailability(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return activityBookingService.checkAllActivityAvailability(date);
    }

    @GetMapping("/activities/availability")
    public Activity checkAvailabilityByActivityName(@RequestParam String name, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return activityBookingService.checkAvailabilityByActivityName(name, date);
    }
}
