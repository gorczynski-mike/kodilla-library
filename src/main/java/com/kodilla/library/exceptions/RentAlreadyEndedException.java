package com.kodilla.library.exceptions;

public class RentAlreadyEndedException extends GenericLibraryException {

    public static final String RENT_ALREADY_ENDED_EXCEPTION = "RENT_ALREADY_ENDED_EXCEPTION";

    public RentAlreadyEndedException(String message) {
        super(message);
    }

}
