package com.kodilla.library.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kodilla.library.domain.LibraryBook;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.util.LocalDateDeserializer;
import com.kodilla.library.util.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibraryRentDto {
    private Long id;
    private LibraryBookDto libraryBook;
    private LibraryUserDto libraryUser;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate rentStartDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate rentEndDate;
}
