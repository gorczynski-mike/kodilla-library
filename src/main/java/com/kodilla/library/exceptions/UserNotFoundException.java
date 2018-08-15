package com.kodilla.library.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long id) {
        super("User not found in database for id: " + id);
    }
}
