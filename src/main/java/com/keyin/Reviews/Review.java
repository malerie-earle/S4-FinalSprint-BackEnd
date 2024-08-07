package com.keyin.Reviews;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String text;

    private int rating;

    @Column(name = "user_name", nullable = false)
    private String user_name;

    public Review() {
        // Default constructor for JPA
    }

    public Review(String text, int rating, String user_name) {
        this.text = text;
        this.rating = rating;
        this.user_name = user_name;
    }

    public Long getReviewId() {
        return review_id;
    }

    public void setReviewId(Long review_id) {
        this.review_id = review_id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
//package com.keyin.Reviews;
//import com.keyin.Users.User;
//import jakarta.persistence.*;
//
//@Entity
//@@ -9,15 +9,25 @@ public class Review {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long review_id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    private String rating;
//    @Lob
//    @Column(columnDefinition = "LONGTEXT")
//    private String text;
//
//    public Long getReviewId() {
//        return review_id;
//    }
//    @@ -26,19 +36,19 @@ public void setReviewId(Long review_id) {
//        this.review_id = review_id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getRating() {
//        return rating;
//    }
//
//    public void setRating(String rating) {
//        this.rating = rating;
//    }
//
//    public String getText() {
//        return text;
//    }
//    public void setText(String text) {
//        this.text = text;
//    }
//}