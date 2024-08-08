package com.keyin.Activity_Booking;
import java.time.LocalDate;

public class Activity_BookingEntryDTO {

        private String userName;
        private Long activity_id;
        private LocalDate date;

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public Long getActivityId() {
                return activity_id;
        }

        public void setActivityId(Long activity_id) {
                this.activity_id = activity_id;
        }

        public LocalDate getDate() {
                return date;
        }

        public void setDate(LocalDate date) {
                this.date = date;
        }
}