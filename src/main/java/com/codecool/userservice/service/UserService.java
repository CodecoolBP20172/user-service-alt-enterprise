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
    public Integer registerUser(User user) {
        if (!doesUserExist(user.getUserName())) {
            userRepository.save(user);
            return user.getId();
        }
        return null;
    }


    // returns a boolean that indicates whether the user exists or not
    public boolean doesUserExist(String username) {
        User temp = userRepository.findByUserName(username);
        return temp != null;
    }

}
