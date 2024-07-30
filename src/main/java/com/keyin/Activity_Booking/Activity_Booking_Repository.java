package com.keyin.Activity_Booking;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Activity_Booking_Repository extends CrudRepository<Activity_Booking,Long> {
}
