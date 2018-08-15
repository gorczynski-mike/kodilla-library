package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Entity(name = "LibraryRents")
public class LibraryRent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = LibraryBook.class)
    private LibraryBook libraryBook;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = LibraryUser.class)
    private LibraryUser libraryUser;

    @Column
    @NotNull
    private LocalDate rentStartDate;

    @Column
    private LocalDate rentEndDate;

}
