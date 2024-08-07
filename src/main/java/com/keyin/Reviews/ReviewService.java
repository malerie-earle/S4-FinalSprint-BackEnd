package com.keyin.Reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewResponseDTO> getAllUserReviews() {
        List<Review> userReviews = reviewRepository.findAll();
        List<ReviewResponseDTO> reviews = new ArrayList<>();

        for (Review review : userReviews) {
            ReviewResponseDTO responseDTO = new ReviewResponseDTO();
            String[] nameParts = review.getUserName().split(" ");
            String fName = nameParts[0];
            String lName = nameParts.length > 1 ? nameParts[1] : "";
            responseDTO.setFName(fName);
            responseDTO.setLName(lName);
            responseDTO.setRating(String.valueOf(review.getRating()));
            responseDTO.setText(review.getText());
            reviews.add(responseDTO);
        }
        return reviews;
    }

    public void createReview(String text, int rating, String user_name) {
        Review review = new Review(text, rating, user_name);
        reviewRepository.save(review);
    }
}
//package com.keyin.Reviews;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.ArrayList;
//import java.util.List;
//
//@@ -10,18 +12,26 @@ public class ReviewService {
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    public List <ReviewResponseDTO> getAllUserReviews(){
//        List<Review> userReviews = reviewRepository.findAll();
//        List<ReviewResponseDTO> reviews = new ArrayList<>();
//        for (Review review : userReviews) {
//            ReviewResponseDTO responseDTO = new ReviewResponseDTO();
//            responseDTO.setFName(review.getUser().getFName());
//            responseDTO.setLName(review.getUser().getLName());
//            responseDTO.setRating(review.getRating());
//            responseDTO.setText(review.getText());
//            reviews.add(responseDTO);
//        }
//        return reviews;
//    }
//}
