package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Entity(name = "LibraryTitles")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "author", "publicationYear"}))
public class LibraryBookTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TITLE_ID", updatable = false, nullable = false)
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String author;

    @Column
    @NotNull
    private int publicationYear;

}
