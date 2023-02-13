package com.chat.chatConfig;

import com.chat.Dtos.response.UserResponse;
import com.corundumstudio.socketio.SocketIOClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserClient {
    private UserResponse user ;
    private SocketIOClient client ;
}
