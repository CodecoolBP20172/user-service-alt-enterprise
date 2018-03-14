package com.codecool.userservice.service;

import com.codecool.userservice.model.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class InitializerBean {

    public InitializerBean(UserService userService) throws ParseException {
        User user1 = new User("Dezső", "Kosztolányi", "dktheprime", "pa55w0rd", "dkdezs@nyugat.hu", "Budapest", 18);
        userService.registerUser(user1);
    }
}
