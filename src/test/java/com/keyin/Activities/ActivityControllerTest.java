package com.keyin.Activities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @Test
    public void getAllActivitiesTest() throws Exception {
        Activity activity1 = new Activity("Swim with Dolphins",
                "Enjoy the crystal blue waters with these amazing creatures.", 10,
                "10:00 AM", "image1.png", "image2.png", "image3.png");
        Activity activity2 = new Activity("Shopping Tour",
                "Shop until you drop.", 12,
                "1:00 PM", "image1.png", "image2.png", "image3.png");
        List<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);

        given(activityService.getAllActivities()).willReturn(activities);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/activities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(activity1.getName())))
                .andExpect(jsonPath("$[1].name", is(activity2.getName())))
                .andExpect(jsonPath("$[0].image1", is(activity1.getImage1())))
                .andExpect(jsonPath("$[1].image1", is(activity2.getImage1())));
    }

        // Test that when getAllActivities() is called on endpoint "/api/activities",
        // it successfully returns a list of activity objects.

    @Test
    public void getActivityByActivityIdTest() throws Exception {
        Long activity_id = 1L;
        Activity activity = new Activity("Swim with Dolphins",
                "Enjoy the crystal blue waters with these amazing creatures.", 10,
                "10:00 AM", "image1.png", "image2.png", "image3.png");
        activity.setActivityId(activity_id);

        given(activityService.getActivityById(activity_id)).willReturn(activity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/activities/{activity_id}", activity_id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityId", is(activity.getActivityId().intValue())))
                .andExpect(jsonPath("$.name", is(activity.getName())))
                .andExpect(jsonPath("$.description", is(activity.getDescription())))
                .andExpect(jsonPath("$.time", is(activity.getTime())));
    }

    // Test that when getActivityById is called with parameter activity_id on endpoint "/api/activities/{activity_id}",
    // it successfully returns the requested Activity Object.
}
