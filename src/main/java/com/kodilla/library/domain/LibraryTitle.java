package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LibraryTitle {

    private Long id;
    private String title;
    private String author;
    private int publicationYear;

}
