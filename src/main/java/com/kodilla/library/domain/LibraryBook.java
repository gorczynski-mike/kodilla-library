package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LibraryBook {

    private Long id;
    private LibraryTitle libraryTitle;
    private LibraryBookStatus libraryBookStatus;

}
