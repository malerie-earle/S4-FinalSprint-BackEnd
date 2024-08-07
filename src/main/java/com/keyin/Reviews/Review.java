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
