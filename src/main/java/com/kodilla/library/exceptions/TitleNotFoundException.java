package com.kodilla.library.exceptions;

public class TitleNotFoundException extends Exception {
    public TitleNotFoundException(Long id) {
        super("Title not found in database for id: " + id);
    }
}
