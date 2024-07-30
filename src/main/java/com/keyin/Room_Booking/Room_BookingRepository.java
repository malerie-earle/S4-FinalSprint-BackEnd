package com.keyin.Room_Booking;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Room_BookingRepository extends CrudRepository<Room_Booking, Long> {
    public List<Room_Booking> findAllByUserId(long id);
    public List<Room_Booking> findAllByRoomId(long id);
}
