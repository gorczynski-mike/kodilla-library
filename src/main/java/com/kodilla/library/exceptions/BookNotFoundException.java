package com.kodilla.library.exceptions;

public class BookNotFoundException extends Exception {

    public static final String BOOK_NOT_FOUND_EXCEPTION = "BOOK_NOT_FOUND_EXCEPTION";

    public BookNotFoundException(String message) {
        super(message);
    }
}
