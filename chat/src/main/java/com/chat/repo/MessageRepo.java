package com.chat.repo;

import com.chat.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message,Long> {
}
