package com.kodilla.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class LibraryUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate accountCreatedDate;
}
