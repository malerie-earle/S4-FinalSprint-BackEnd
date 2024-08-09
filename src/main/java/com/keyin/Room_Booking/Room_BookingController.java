package com.keyin.Room_Booking;

import com.keyin.Activities.Activity;
import com.keyin.Room_Booking.Room_BookingDTObjects.BookingRequest;

import com.keyin.Users.User;
import com.keyin.Users.UserRepository;
import com.keyin.Rooms.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class Room_BookingController {
    @Autowired
    private Room_BookingService room_bookingService;
    @Autowired
    private UserRepository userRepository;

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

    /*
    @PostMapping("api/rooms/book")
    public Room_Booking bookRoom(@RequestParam(value = "user_id") Long userid, @RequestParam(value = "room_id") Long roomid, @RequestParam(value = "start") String start, @RequestParam(value = "end") String end){
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
     */

    @PostMapping("api/rooms/book")
    public Room_Booking bookRoom(@RequestBody BookingRequest bookingRequest) {

        String userName = bookingRequest.getUsername();
        List<User> users = userRepository.findAllByUsername(userName);

        if (!users.isEmpty()) {
            return room_bookingService.createRoom_Booking(users.get(0).getUserId(), bookingRequest.getRoom_id(), bookingRequest.getStart(), bookingRequest.getEnd());
        }
        return null;
    }

    @GetMapping("api/rooms/availability")
    public List<Room> getAvailableRoomsByDateRange(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return room_bookingService.getAvailableRoomsByDateRange(startDate, endDate);
    }

    @GetMapping("api/rooms/availability/occupancy")
    public List<Room> getAvailableRoomsByDateRangeAndOccupancy(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam int requestedOccupancy) {
        return room_bookingService.getAvailableRoomsByDateRangeAndOccupancy(startDate, endDate, requestedOccupancy);
    }

    @GetMapping("api/rooms/availability/type")
    public List<Room> getAvailableRoomsByDateRangeAndType(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String roomType) {
        return room_bookingService.getAvailableRoomsByDateRangeAndType(startDate, endDate, roomType);
    }

    @GetMapping("api/rooms/availability/occupancy/type")
    public List<Room> getAvailableRoomsByDateRangeOccupancyAndType(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam int requestedOccupancy, @RequestParam String roomType) {
        return room_bookingService.getAvailableRoomsByDateRangeOccupancyAndType(startDate, endDate, requestedOccupancy, roomType);
    }
}
