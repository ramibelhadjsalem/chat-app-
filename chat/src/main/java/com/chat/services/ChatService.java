package com.chat.services;

import com.chat.Dtos.request.MessageDto;
import com.chat.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.webjars.NotFoundException;

@Service
public class ChatService {
    @Autowired private RoomService roomService;
    @Autowired private UserService userService;
    @Autowired private MessageService messageService;


    public Message saveMessage(MessageDto messageDto,Long senderId){
        var sender = userService.findByidOptionel(senderId);
        var roomOptional = roomService.findById(messageDto.getRoom());
        if(roomOptional ==null) throw new NotFoundException("room not found with that id");
        var roomToSave = roomOptional.get();

        if(roomToSave.getMembers().contains(sender.get())==false) throw new  NotFoundException("not allowed to access this room");
        Message message = Message.builder().sender(sender.get()).content(messageDto.getContent()).room(roomToSave).build();
        roomToSave.getMessages().add(message);
        return messageService.save(message);

    }
}
