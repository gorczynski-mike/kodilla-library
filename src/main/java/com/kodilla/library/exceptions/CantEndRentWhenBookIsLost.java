package com.kodilla.library.exceptions;

public class CantEndRentWhenBookIsLost extends GenericLibraryException {

    public static final String CANT_END_RENT_WHEN_BOOK_LOST_EXCEPTION = "CANT_END_RENT_WHEN_BOOK_LOST_EXCEPTION";

    public CantEndRentWhenBookIsLost(String message) {
        super(message);
    }

}
