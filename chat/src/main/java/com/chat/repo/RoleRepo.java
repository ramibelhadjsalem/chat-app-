package com.chat.repo;

import com.chat.models.ERole;
import com.chat.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
