package com.kodilla.library.exceptions;

public class CantPayForBookNotLost extends GenericLibraryException {

    public static final String CANT_PAY_FOR_BOOK_NOT_LOST_EXCEPTION = "CANT_PAY_FOR_BOOK_NOT_LOST_EXCEPTION";

    public CantPayForBookNotLost(String message) {
        super(message);
    }
}
