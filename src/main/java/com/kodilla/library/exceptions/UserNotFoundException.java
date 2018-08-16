package com.kodilla.library.exceptions;

public class UserNotFoundException extends GenericLibraryException {

    public static final String USER_NOT_FOUND_EXCEPTION = "USER_NOT_FOUND_EXCEPTION";

    public UserNotFoundException(String message) {
        super(message);
    }
}
