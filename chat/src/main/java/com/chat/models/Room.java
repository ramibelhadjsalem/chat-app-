package com.chat.models;

import com.chat.Dtos.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room extends  BaseEntity{

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonView(View.base.class)
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonView(View.base.class)
    @OrderBy("createdAt DESC")
    private Collection<Message> messages = new ArrayList<>();

}
