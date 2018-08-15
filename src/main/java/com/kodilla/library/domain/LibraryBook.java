package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity(name = "LibraryBooks")
public class LibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = LibraryTitle.class)
    private LibraryTitle libraryTitle;

    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private LibraryBookStatus libraryBookStatus;

}
