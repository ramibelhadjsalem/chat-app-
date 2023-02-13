package com.chat.Dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class JwtResponse {
    private String token, refresh_token,username,email,userimg;
    private String type = "Bearer";
    private Long id;
    private List<String> roles;
    public JwtResponse(String accessToken,String refresh_token, Long id, String username, String email, List<String> roles,String userimg) {
        this.token = accessToken;
        this.refresh_token = refresh_token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.userimg =userimg;
    }
}
