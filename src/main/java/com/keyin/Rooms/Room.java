package com.keyin.Rooms;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;

@Entity
public class Room {

    @Id
    @SequenceGenerator(name = "room_sequence", sequenceName = "room_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_sequence")
    private long room_id;

    private int room_number;
    private String room_name;
    private String view;
    private String type;

    private String beds;
    private int occupancy;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String image1;

    @Column(columnDefinition = "LONGTEXT")
    private String image2;

    @Column(columnDefinition = "LONGTEXT")
    private String image3;

    public Room(long room_id, int room_number, String room_name, String view, String type, String beds, int occupancy, String description, String image1, String image2, String image3) {
        this.room_id = room_id;
        this.room_number = room_number;
        this.room_name = room_name;
        this.view = view;
        this.type = type;
        this.beds = beds;
        this.occupancy = occupancy;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Room(int room_number, String room_name, String view, String type, String beds, int occupancy, String description, String image1, String image2, String image3) {
        this.room_number = room_number;
        this.room_name = room_name;
        this.view = view;
        this.type = type;
        this.beds = beds;
        this.occupancy = occupancy;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    // Getters and Setters

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
