package com.chat.Dtos.request;


import javax.validation.constraints.NotBlank;

public class SignUpDto {
    @NotBlank
    private String username ;
    @NotBlank
    private String password ;
    @NotBlank
    private  String email;

    public SignUpDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public SignUpDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
