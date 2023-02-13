package com.chat.services;

import com.chat.models.ERole;
import com.chat.models.Role;
import com.chat.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired private RoleRepo roleRepository;
    public Optional<Role> findByName(ERole name){
        return roleRepository.findByName(name);
    }
}
