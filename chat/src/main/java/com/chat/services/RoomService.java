package com.chat.services;

import com.chat.models.Room;
import com.chat.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired private RoomRepo roomRepo ;
    public List<Room> findUserRoom(Long id_user){
        return roomRepo.findRoomsByUserId(id_user);
    }

    public Optional<Room> findById(Long room) {
        return roomRepo.findById(room);
    }
    public Room save(Room room){
        return roomRepo.save(room);
    }
}
