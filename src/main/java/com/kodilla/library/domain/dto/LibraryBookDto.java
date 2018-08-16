package com.kodilla.library.domain.dto;

import com.kodilla.library.domain.LibraryBookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibraryBookDto {
    private Long id;
    private LibraryBookTitleDto libraryBookTitle;
    private LibraryBookStatus libraryBookStatus;
}
