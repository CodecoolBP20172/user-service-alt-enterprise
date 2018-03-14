package com.codecool.userservice.controller;

import com.codecool.userservice.model.User;
import com.codecool.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControllerREST {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> userById(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
