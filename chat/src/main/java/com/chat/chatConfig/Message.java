package com.chat.chatConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Message {
    @NotBlank
    private String content;
    private String senderName;
    private String targetUserName;
}
