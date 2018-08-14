package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class LibraryRent {

    private Long id;
    private LibraryBook libraryBook;
    private LibraryUser libraryUser;
    private LocalDate rentStartDate;
    private LocalDate rentEndDate;

}
