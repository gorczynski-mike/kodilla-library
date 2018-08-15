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
    @Column(name = "BOOK_ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = LibraryBookTitle.class)
    @JoinColumn(name = "TITLE_ID")
    private LibraryBookTitle libraryBookTitle;

    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private LibraryBookStatus libraryBookStatus;

}
