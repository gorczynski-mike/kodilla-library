package com.kodilla.library.exceptions;

public class RentNotFoundException extends GenericLibraryException {

    public static final String RENT_NOT_FOUND_EXCEPTION = "RENT_NOT_FOUND_EXCEPTION";

    public RentNotFoundException(String message) {
        super(message);
    }

}
