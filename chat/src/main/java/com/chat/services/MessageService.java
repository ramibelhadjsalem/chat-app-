package com.chat.services;

import com.chat.models.Message;
import com.chat.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired private MessageRepo messageRepo;

    public Message save(Message message){
        return messageRepo.save(message);
    }
}
