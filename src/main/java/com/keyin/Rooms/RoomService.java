package com.keyin.Rooms;

import com.keyin.Room_Booking.Room_Booking;
import com.keyin.Room_Booking.Room_BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;


    public List<Room> getAllRooms(){
        return (List<Room>) roomRepository.findAll();
    }

    public Room getRoomByID(long id){
        Optional<Room> result = roomRepository.findById(id);

        if(result.isPresent()){
            return result.get();
        }

        return null;
    }

    public List<Room> getRoomsFilteredBySearch(Date start, Date end, int occupancy, String type, String view){
        Room_BookingService room_bookingService = new Room_BookingService();

        List<Room> availableRooms = (List<Room>) roomRepository.findAll();

        //rooms that do not meet the occupancy requirement
        if(occupancy != 0){
            availableRooms = availableRooms.stream().filter(room -> room.getOccupancy() > occupancy).collect(Collectors.toList());
        }

        if(type != null && !type.equals("")){
            availableRooms = availableRooms.stream().filter(room -> room.getType().contains(type)).collect(Collectors.toList());
        }

        if(view != null && !view.equals("")){
            availableRooms = availableRooms.stream().filter(room -> room.getView().contains(view)).collect(Collectors.toList());
        }

        List<Room_Booking> conflictingBookingsFromDateRange = room_bookingService.getConflictingRoomBookings(start, end);

        //remove left over rooms that conflict with date range
        for(Room_Booking booking : conflictingBookingsFromDateRange){
            for(Room room: availableRooms){
                if(room.getRoom_id() == booking.getRoom().getRoom_id()){
                    availableRooms.remove(room);
                }
            }
        }

        return availableRooms;
    }
}
