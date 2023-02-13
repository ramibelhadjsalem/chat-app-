package com.chat.chatConfig.validators;

import com.chat.Dtos.request.MessageDto;
import com.chat.chatConfig.Message;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.util.StringUtils;

@Component
public class MessageValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MessageDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MessageDto message = (MessageDto) target;
        if (isNullOrBlank(message.getContent())) {
            errors.rejectValue("content", "Message text cannot be longer than 140 characters.");
        }
        if(message.getRoom()==null || message.getRoom()<0 ){
            errors.rejectValue("senderName", "message.required", "room is Required");
        }

    }

    public static boolean isNullOrBlank(String param) {
        if(param ==null) return true;
        if(param.isEmpty()) return true;
        return false;

    }
}