package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "LibraryBooks")
public class LibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOK_ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = LibraryBookTitle.class)
    @JoinColumn(name = "TITLE_ID")
    private LibraryBookTitle libraryBookTitle;

    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private LibraryBookStatus libraryBookStatus;

}
