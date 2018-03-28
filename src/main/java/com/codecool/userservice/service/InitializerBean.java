package com.codecool.userservice.service;

import com.codecool.userservice.model.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class InitializerBean {

    public InitializerBean(UserService userService) throws ParseException {
        User user1 = new User("Dezső", "Kosztolányi", "dktheprime", "pa55w0rd", "dkdezs@nyugat.hu", "Budapest", 18);
        User user2 = new User("Babra", "Megy", "username", "psw", "email@email.hu", "City", 10000);
        User user3 = new User("Málészájú", "Kecskefia", "babababa", "qwe", "asd@asd.hu", "Város", 200000000);
        userService.registerUser(user1);
        userService.registerUser(user2);
        userService.registerUser(user3);
    }
}
