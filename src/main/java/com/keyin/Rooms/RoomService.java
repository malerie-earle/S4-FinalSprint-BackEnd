package com.keyin.Rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}
