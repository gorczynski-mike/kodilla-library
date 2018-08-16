package com.kodilla.library.exceptions;

public class TitleAlreadyExistsException extends GenericLibraryException {

    public TitleAlreadyExistsException() {
        super("TITLE_ALREADY_EXISTS_EXCEPTION : at least one of the following data must be different in order to create " +
                "new title: [author, title, publication year]");
    }
}
