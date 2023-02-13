package com.chat.models;

import com.chat.Dtos.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor@NoArgsConstructor
@Builder
public class Message extends BaseEntity {
    @JsonView(View.base.class)
    private String content;
    @ManyToOne
    @JsonView(View.base.class)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="room_id", nullable=false)
    private Room room;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
