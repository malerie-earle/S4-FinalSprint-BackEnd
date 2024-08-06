package com.keyin.Reviews;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void getAllUserReviewsTest() throws Exception {

        ReviewResponseDTO review1 = new ReviewResponseDTO();
        review1.setFName("Donald");
        review1.setLName("Duck");
        review1.setRating("5.0");
        review1.setText("Fantastic place to stay for your family vacation.");

        ReviewResponseDTO review2 = new ReviewResponseDTO();
        review1.setFName("Mini");
        review1.setLName("Mouse");
        review1.setRating("5.0");
        review1.setText("I loved all the activities the resort offered.");

        List<ReviewResponseDTO> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);

        given(reviewService.getAllUserReviews()).willReturn(reviews);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fname", is(review1.getFName())))
                .andExpect(jsonPath("$[0].lname", is(review1.getLName())))
                .andExpect(jsonPath("$[0].rating", is(review1.getRating())))
                .andExpect(jsonPath("$[0].text", is(review1.getText())))
                .andExpect(jsonPath("$[1].fname", is(review2.getFName())))
                .andExpect(jsonPath("$[1].lname", is(review2.getLName())))
                .andExpect(jsonPath("$[1].rating", is(review2.getRating())))
                .andExpect(jsonPath("$[1].text", is(review2.getText())));
    }

    // Test that when getAllUserReviews is called on endpoint "/api/reviews",
    // it returns a list of review objects.
}
