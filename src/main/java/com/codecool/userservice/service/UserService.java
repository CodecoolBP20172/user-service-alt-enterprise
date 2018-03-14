package com.codecool.userservice.service;

import com.codecool.userservice.model.User;
import com.codecool.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User getUserById(int id){
        return userRepository.findOne(id);
    }

    // returns with the id of the created user
    public int registerUser(User user) {
        userRepository.save(user);
        return user.getId();
    }


    // returns a boolean that indicates whether the user exists or not
    public boolean doesUserExist(String username) {
        for (User user : userRepository.findAll()) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
