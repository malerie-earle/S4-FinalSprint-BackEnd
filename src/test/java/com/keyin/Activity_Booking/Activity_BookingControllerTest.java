//package com.keyin.Activity_Booking;
//
//import com.keyin.Activities.Activity;
//import com.keyin.Users.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(Activity_BookingController.class)
//public class Activity_BookingControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private Activity_BookingService activityBookingService;
//
//    @MockBean
//    private Activity_BookingRepository activityBookingRepository;
//
//    @Test
//    public void checkAllActivityAvailabilityTest() throws Exception {
//        LocalDate date = LocalDate.of(2024, 8, 5);
//        Activity activity1 = new Activity("Swim with Dolphins", "Enjoy the crystal blue waters with these amazing creatures.", 10,
//                "10:00 AM", "image1.png", "image2.png","image3.png");
//        Activity activity2 = new Activity("Shopping Tour", "Shop until you drop.", 12,
//                "1:00 PM", "image1.png","image2.png", "image3.png");
//
//        List<Activity> activities = new ArrayList<>();
//        activities.add(activity1);
//        activities.add(activity2);
//
//        given(activityBookingService.checkAllActivityAvailability(date)).willReturn(activities);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/activities/availability/all")
//                        .param("date", date.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is(activity1.getName())))
//                .andExpect(jsonPath("$[1].name", is(activity2.getName())))
//                .andExpect(jsonPath("$[0].spots", is(activity1.getSpots())))
//                .andExpect(jsonPath("$[1].spots", is(activity2.getSpots())));
//    }
//
//    // Test that the endpoint correctly handles a LocalDate parameter and
//    // returns a list of Activity objects.
//
//    @Test
//    public void createActivityBookingTest() throws Exception {
//        Activity_BookingEntryDTO activityBookingRequest = new Activity_BookingEntryDTO();
//        activityBookingRequest.setUserId(1L);
//        activityBookingRequest.setActivityId(1L);
//        activityBookingRequest.setDate(LocalDate.of(2024, 8, 5));
//
//        User user = new User(dto.getUsername(), dto.getAttributes());
//        user.setUserId(1);
//        Activity activity = new Activity();
//        activity.setActivityId(1L);
//
//        Activity_Booking databaseBooking = new Activity_Booking();
//        databaseBooking.setUser(user);
//        databaseBooking.setActivity(activity);
//        databaseBooking.setDate(LocalDate.of(2024, 8, 5));
//
//        given(activityBookingService.createActivityBooking(1L, 1L, LocalDate.of(2024, 8, 5))).willReturn(databaseBooking);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/activities/book")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"userId\": 1, \"activityId\": 1, \"date\": \"2024-08-05\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.user.userId", is(databaseBooking.getUser().getUserId())))
//                .andExpect(jsonPath("$.activity.activityId", is(databaseBooking.getActivity().getActivityId().intValue())))
//                .andExpect(jsonPath("$.date", is(databaseBooking.getDate().toString())));
//    }
//
//    // Test that endpoint "/api/activities/book" accepts the DTO object and
//    // returns the new created activity_booking object.
//
//    @Test
//    public void getActivityBookingsByUserIdTest() throws Exception {
//        User user = new User(dto.getUsername(), dto.getAttributes());
//        user.setUserId(1);
//        Activity activity1 = new Activity();
//        activity1.setActivityId(1L);
//        Activity activity2 = new Activity();
//        activity2.setActivityId(2L);
//
//        Activity_Booking activityBooking1 = new Activity_Booking();
//        activityBooking1.setUser(user);
//        activityBooking1.setActivity(activity1);
//        activityBooking1.setDate(LocalDate.of(2024, 8, 5));
//
//        Activity_Booking activityBooking2 = new Activity_Booking();
//        activityBooking2.setUser(user);
//        activityBooking2.setActivity(activity2);
//        activityBooking2.setDate(LocalDate.of(2024, 8, 6));
//
//        List<Activity_Booking> userBookings = new ArrayList<>();
//        userBookings.add(activityBooking1);
//        userBookings.add(activityBooking2);
//
//        given(activityBookingService.getActivityBookingsByUserId(user.getUserId())).willReturn(userBookings);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/activities/bookings/user/{user_id}", user.getUserId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].user.userId", is(user.getUserId())))
//                .andExpect(jsonPath("$[0].activity.activityId", is(activityBooking1.getActivity().getActivityId().intValue())))
//                .andExpect(jsonPath("$[0].date", is(activityBooking1.getDate().toString())))
//                .andExpect(jsonPath("$[1].user.userId", is(user.getUserId())))
//                .andExpect(jsonPath("$[1].activity.activityId", is(activityBooking2.getActivity().getActivityId().intValue())))
//                .andExpect(jsonPath("$[1].date", is(activityBooking2.getDate().toString())));
//    }
//
//    // Teat that when getActivityBookingsByUserId is called on endpoint "/api/activities/bookings/user/{user_id}",
//    // it returns a list of activity_booking objects for the provided user_id.
//}
//
