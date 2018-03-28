package com.codecool.userservice.service;

import com.codecool.userservice.customException.UserNameAlreadyTakenException;
import com.codecool.userservice.model.User;
import com.codecool.userservice.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User getUserById(int id) {
        return userRepository.findOne(id);
    }

    // returns with the id of the created user
    public Integer registerUser(User user) throws IllegalArgumentException, UserNameAlreadyTakenException {
        if (!userFieldValidator(user)) {
            throw new IllegalArgumentException();
        }
        if (doesUserExist(user.getUserName())) {
            throw new UserNameAlreadyTakenException();
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return user.getId();
    }


    // returns a boolean that indicates whether the user exists or not
    public boolean doesUserExist(String username) {
        User temp = userRepository.findByUserName(username);
        return temp != null;
    }

    public boolean doesUserExist(Integer userId) {
        User temp = getUserById(userId);
        return temp != null;
    }

    public boolean loginUser(String userName, String password) {
        User temp = userRepository.findByUserName(userName);
        return BCrypt.checkpw(password, temp.getPassword());
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public boolean deleteUser(int userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return false;
        }
        userRepository.delete(userId);
        user = userRepository.findOne(userId);
        return user == null;
    }

    // returns with true if the field is empty
    private boolean userFieldIsEmpty(String field) {
        if (field.length() == 0) {
            return true;
        }
        String cleanedField = field.replaceAll("\\s", "");
        return cleanedField.length() == 0;
    }

    // returns true if the given user's fields hold valid values
    // checks if names only contains letters and start with uppercase letters
    // checks if email contains @ and .
    // checks if fields are empty or if they only contain whitespaces
    public boolean userFieldValidator(User user) {
        boolean noFieldIsEmpty = !(userFieldIsEmpty(user.getFirstName())
                & userFieldIsEmpty(user.getLastName())
                & userFieldIsEmpty(user.getUserName())
                & userFieldIsEmpty(user.getPassword())
                & userFieldIsEmpty(user.getEmail())
                & userFieldIsEmpty(user.getCity()));

        boolean nameFieldsAreValid = fieldIsName(user.getFirstName())
                & fieldIsName(user.getLastName())
                & fieldIsName(user.getCity());

        boolean emailValid = isEmail(user.getEmail());

        return noFieldIsEmpty & nameFieldsAreValid & emailValid;
    }

    private boolean isEmail(String email) {
        return email.matches("(.+)[@](.+)[.](.{2,})");
    }

    // returns true if the given field is a name
    // Starts with uppercase and only contains letters
    private boolean fieldIsName(String field) {
        Character ch = field.charAt(0);
        boolean firstLetterUppercase = Character.isUpperCase(ch);
        boolean noNumbers = !field.matches("[0-9]+");
        return firstLetterUppercase & noNumbers;
    }

    public void updateUser(User userToUpdate) {
        userRepository.save(userToUpdate);
    }
}
