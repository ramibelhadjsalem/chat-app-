package com.chat.services;

import com.chat.models.User;
import com.chat.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepo userRepository;
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public  boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id_user) {
        try {
            return userRepository.findById(id_user).get();
        }catch(Exception ex){
            throw new NotFoundException("user with id :"+id_user+" not found");
        }
    }
    public Optional<User> findByidOptionel(Long id){
        return userRepository.findById(id);
    }
}
