package com.kodilla.library.exceptions;

public class BookNotAvailableException extends GenericLibraryException {

    public static final String BOOK_NOT_AVAILABLE_EXCEPTION = "BOOK_NOT_AVAILABLE_EXCEPTION";

    public BookNotAvailableException(String message) {
        super(message);
    }

}
