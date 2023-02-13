package com.chat.controller;

import com.chat.Dtos.view.View;
import com.chat.models.Message;
import com.chat.models.Room;
import com.chat.repo.MessageRepo;
import com.chat.repo.RoomRepo;
import com.chat.repo.UserRepo;
import com.chat.services.RoomService;
import com.chat.utils.TokenUtils;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired private RoomService roomService;
    @Autowired private TokenUtils tokenUtils;

    @GetMapping
    @JsonView(View.base.class)
    public List<Room> findAllOwwnRomms(){
        return roomService.findUserRoom(tokenUtils.ExtractId());
    }
    @PostMapping
    public Room saveRoom(){
        return roomService.save(Room.builder().build());
    }

}
