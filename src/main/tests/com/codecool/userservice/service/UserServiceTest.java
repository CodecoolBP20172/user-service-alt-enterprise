package com.codecool.userservice.service;

import com.codecool.userservice.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    public UserService userService = new UserService();


    @Test
    void registerUserWithBadFirstName() {
        User test = new User("asd", "Asd", "asdasd", "psw", "good@email.com", "City", 0);
        assertFalse(userService.userFieldValidator(test));
    }

    @Test
    void registerUserBadLastName() {
        User test = new User("Asd", "asd", "asdasd", "psw", "good@email.com", "City", 0);
        assertFalse(userService.userFieldValidator(test));
    }

    @Test
    void registerUserBadEmailNoKukac() {
        User test = new User("Asd", "Asd", "asdasd", "psw", "goodemail.com", "City", 0);
        assertFalse(userService.userFieldValidator(test));
    }

    @Test
    void regUserBadEmailNoDot() {
        User test = new User("Asd", "Asd", "asdasd", "psw", "good@emailcom", "City", 0);
        assertFalse(userService.userFieldValidator(test));
    }

    @Test
    void regUserBadEmailNothingBetweenKukacAndDot() {
        User test = new User("Asd", "Asd", "asdasd", "psw", "good@.com", "City", 0);
        assertFalse(userService.userFieldValidator(test));
    }

    @Test
    void regUserBadCity() {
        User test = new User("Asd", "Asd", "asdasd", "psw", "good@email.com", "badCity", 0);
        assertFalse(userService.userFieldValidator(test));
    }

    @Test
    void regUserAllGood() {
        User test = new User("Asd", "Asd", "asdasd", "psw", "good@email.com", "City", 0);
        assertTrue(userService.userFieldValidator(test));
    }
}