package com.keyin.Reviews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List <ReviewResponseDTO> getAllUserReviews(){
        List<Review> userReviews = reviewRepository.findAll();
        List<ReviewResponseDTO> reviews = new ArrayList<>();
        for (Review review : userReviews) {
            ReviewResponseDTO responseDTO = new ReviewResponseDTO();
            responseDTO.setFName(review.getUser().getFname());
            responseDTO.setLName(review.getUser().getLName());
            responseDTO.setRating(review.getRating());
            responseDTO.setText(review.getText());
            reviews.add(responseDTO);
        }
        return reviews;
    }
}

