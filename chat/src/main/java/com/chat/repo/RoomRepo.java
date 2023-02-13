package com.chat.repo;

import com.chat.models.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room,Long> {

    @Query("SELECT r FROM Room r  JOIN r.members m WHERE m.id = :userId ORDER BY r.updatedAt DESC")
    List<Room> findRoomsByUserId(@Param("userId") Long userId);



}
