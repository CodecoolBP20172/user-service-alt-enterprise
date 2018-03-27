package com.codecool.userservice.controller;

import com.codecool.userservice.model.User;
import com.codecool.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody Map<String, String> data ){
        String firstName = data.get("firstName");
        String lastName = data.get("lastName");
        String email = data.get("email");
        String userName = data.get("userName");
        String password = data.get("password");
        String city = data.get("city");

        User newUser = new User(firstName, lastName, userName, password, email, city, 0);
        Integer response = userService.registerUser(newUser);
        if (response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
//        return new ResponseEntity<>("Username already taken!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Username already taken!",HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginUser(@RequestBody Map<String, String> data){
        String userName = data.get("userName");
        String password = data.get("password");

        if (userService.doesUserExist(userName)){
            if (userService.loginUser(userName, password)){
                HashMap<String, String> response = new HashMap<>();
                User temp = userService.getUserByUserName(userName);
                response.put("userName", temp.getUserName());
                response.put("userId", String.valueOf(temp.getId()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Login failed", HttpStatus.OK);
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable int userId) {
        if (userService.deleteUser(userId)) {
            return new ResponseEntity<>("Success deleting user:" + userId, HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete user with id: " + userId + "\n", HttpStatus.BAD_REQUEST);
    }
}
