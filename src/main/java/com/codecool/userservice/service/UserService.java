package com.codecool.userservice.service;

import com.codecool.userservice.model.User;
import com.codecool.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

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
            String hashedPassword = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
            user.setPassword(hashedPassword);
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

    public boolean loginUser(String userName, String password){
        User temp = userRepository.findByUserName(userName);
        return BCrypt.checkpw(password, temp.getPassword());
    }

    public User getUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
