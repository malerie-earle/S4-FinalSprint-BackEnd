package com.keyin.Activities;

import jakarta.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activity_id;

    private String name;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private int spots;

    private String time;

    @Column(length = 500)
    private String image1;

    @Column(length = 500)
    private String image2;

    @Column(length = 500)
    private String image3;

    public Activity(String s, String s1, int i, String s2, String url, String url1, String url2) {
    }

    // Getters and setters
    public Long getActivityId() {
        return activity_id;
    }

    public void setActivityId(Long activity_id) {
        this.activity_id = activity_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpots() {
        return spots;
    }

    public void setSpots(int spots) {
        this.spots = spots;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }
}
