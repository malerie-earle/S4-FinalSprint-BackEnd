package com.keyin.Room_Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Room_BookingService {
    @Autowired
    private Room_BookingRepository room_bookingRepository;

    public List<Room_Booking> getAllRoom_Bookings(){
        return (List<Room_Booking>) room_bookingRepository.findAll();
    }

    public Room_Booking getRoom_BookingById(long id){
        Optional<Room_Booking> result = room_bookingRepository.findById(id);

        if(result.isPresent()){
            return result.get();
        }

        return null;
    }

    public List<Room_Booking> getRoom_BookingByRoomId(long id){
        return room_bookingRepository.findAllByRoomId(id);
    }

    public List<Room_Booking> getRoom_BookingByUserId(long id){
        return room_bookingRepository.findAllByUserId(id);
    }
}
