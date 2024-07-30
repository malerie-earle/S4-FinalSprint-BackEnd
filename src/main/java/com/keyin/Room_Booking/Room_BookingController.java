package com.keyin.Room_Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class Room_BookingController {
    @Autowired
    private Room_BookingService room_bookingService;

    @GetMapping("api/rooms/bookings")
    public List<Room_Booking> getAllRoomBookings(){
        return room_bookingService.getAllRoom_Bookings();
    }

    @GetMapping("api/rooms/bookings/{id}")
    public Room_Booking getRoom_Booking(@PathVariable Long id){
        return room_bookingService.getRoom_BookingById(id);
    }

    @GetMapping("api/rooms/bookings/user/{id}")
    public List<Room_Booking> getRoom_BookingsByUserId(@PathVariable Long id){
        return room_bookingService.getRoom_BookingByUserId(id);
    }

    @GetMapping("api/rooms/bookings/room/{id}")
    public List<Room_Booking> getRoom_BookingsByRoomId(@PathVariable Long id){
        return room_bookingService.getRoom_BookingByRoomId(id);
    }
}
