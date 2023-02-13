package com.chat.models;

import com.chat.Dtos.view.View;
import com.corundumstudio.socketio.SocketIOClient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class User extends BaseEntity{
    @JsonView(View.base.class)
    private String email ,username ,userimg;
    @JsonIgnore
    private String password ;
    private Boolean verified = false;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

}
