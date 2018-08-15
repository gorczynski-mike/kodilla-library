package com.kodilla.library.mapper;

import com.kodilla.library.domain.LibraryBookTitle;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dto.LibraryBookTitleDto;
import com.kodilla.library.domain.dto.LibraryUserDto;
import org.springframework.stereotype.Component;

@Component
public class LibraryMapper {

    public LibraryUser mapUserDtoToUser(LibraryUserDto libraryUserDto) {
        return new LibraryUser(libraryUserDto.getId(), libraryUserDto.getFirstName(), libraryUserDto.getLastName(),
                libraryUserDto.getAccountCreatedDate());
    }

    public LibraryUserDto mapUserToUserDto(LibraryUser libraryUser) {
        return new LibraryUserDto(libraryUser.getId(), libraryUser.getFirstName(), libraryUser.getLastName(),
                libraryUser.getAccountCreatedDate());
    }

    public LibraryBookTitle mapBookTitleDtoToBookTitle(LibraryBookTitleDto libraryBookTitleDto) {
        return new LibraryBookTitle(libraryBookTitleDto.getId(), libraryBookTitleDto.getTitle(),
                libraryBookTitleDto.getAuthor(), libraryBookTitleDto.getPublicationYear());
    }

    public LibraryBookTitleDto mapBookTitleToBookTitleDto(LibraryBookTitle libraryBookTitle) {
        return new LibraryBookTitleDto(libraryBookTitle.getId(), libraryBookTitle.getTitle(),
                libraryBookTitle.getAuthor(), libraryBookTitle.getPublicationYear());
    }

}
