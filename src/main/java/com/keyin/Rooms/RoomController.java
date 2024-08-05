package com.keyin.Rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/api/rooms")
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/api/rooms/{id}")
    public Room getRoom(@PathVariable Long id){
        return roomService.getRoomByID(id);
    }

    @PostMapping("/api/rooms/available")
    public List<Room> getAvailableRooms(@RequestParam(value = "start") Date startDate, @RequestParam(value = "end") Date endDate, @RequestParam(value = "occupancy") int occupancy, @RequestParam(value = "type") String type, @RequestParam(value = "view") String view){
        return roomService.getRoomsFilteredBySearch(startDate,endDate,occupancy, type, view);
    }
}
