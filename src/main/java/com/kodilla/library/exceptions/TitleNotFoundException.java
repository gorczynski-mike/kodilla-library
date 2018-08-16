package com.kodilla.library.exceptions;

public class TitleNotFoundException extends GenericLibraryException {

    public static final String TITLE_NOT_FOUND_EXCEPTION = "TITLE_NOT_FOUND_EXCEPTION";

    public TitleNotFoundException(String message) {
        super(message);
    }
}
