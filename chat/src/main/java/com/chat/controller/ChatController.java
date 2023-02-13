package com.chat.controller;

import com.chat.Dtos.request.MessageDto;
import com.chat.services.ChatService;
import com.chat.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired private ChatService chatService;
    @Autowired private TokenUtils tokenUtils;
    @PostMapping
    public ResponseEntity sendMessage(@Valid @RequestBody MessageDto messageDto){
        chatService.saveMessage(messageDto,tokenUtils.ExtractId());
        return ResponseEntity.ok().build();
    }
}
