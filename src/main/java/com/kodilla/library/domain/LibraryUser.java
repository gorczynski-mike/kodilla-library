package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Entity(name = "LibraryUsers")
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @OneToMany(mappedBy = "libraryUser", fetch = FetchType.LAZY)
    private List<LibraryRent> rents = new ArrayList<>();

}
