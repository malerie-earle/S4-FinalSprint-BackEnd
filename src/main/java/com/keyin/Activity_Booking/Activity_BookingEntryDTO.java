package com.keyin.Activity_Booking;

import java.time.LocalDate;

public class Activity_BookingEntryDTO {

        private Long user_id;
        private Long activity_id;
        private LocalDate date;

        public Long getUserId() {
                return user_id;
        }

        public void setUserId(Long user_id) {
                this.user_id = user_id;
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
