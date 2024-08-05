package com.keyin.Room_Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @PostMapping("api/rooms/book")
    public Room_Booking bookRoom(@RequestParam(value = "userid") Long userid, @RequestParam(value = "roomid") Long roomid, @RequestParam(value = "start") String start, @RequestParam(value = "end") String end){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date startD;
        Date endD;
        try {
            startD = formatter.parse(start);
            endD = formatter.parse(end);
        }catch (Exception e){
            return null;
        }
        return room_bookingService.createRoom_Booking(userid,roomid,startD,endD);
    }
}
