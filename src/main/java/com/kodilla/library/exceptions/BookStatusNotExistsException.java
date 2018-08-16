package com.kodilla.library.exceptions;

public class BookStatusNotExistsException extends GenericLibraryException{

    public static final String BOOK_STATUS_NOT_EXISTS_EXCEPTION = "BOOK_STATUS_NOT_EXISTS_FOUND_EXCEPTION";

    public BookStatusNotExistsException(String message) {
        super(message);
    }
}
