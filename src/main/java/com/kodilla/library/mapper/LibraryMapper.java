package com.kodilla.library.mapper;

import com.kodilla.library.domain.LibraryBook;
import com.kodilla.library.domain.LibraryBookTitle;
import com.kodilla.library.domain.LibraryRent;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dto.LibraryBookDto;
import com.kodilla.library.domain.dto.LibraryBookTitleDto;
import com.kodilla.library.domain.dto.LibraryRentDto;
import com.kodilla.library.domain.dto.LibraryUserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public LibraryBookDto mapLibraryBookToLibraryBookDto(LibraryBook libraryBook) {
        return new LibraryBookDto(libraryBook.getId(), libraryBook.getLibraryBookTitle(),
                libraryBook.getLibraryBookStatus());
    }

    public List<LibraryBookDto> mapLibraryBookListToLibraryBookDtoList(List<LibraryBook> libraryBooks) {
        return libraryBooks.stream()
                .map(book ->  new LibraryBookDto(book.getId(), book.getLibraryBookTitle(), book.getLibraryBookStatus()))
                .collect(Collectors.toList());
    }

    public List<LibraryRentDto> mapLibraryRentListToLibraryRentDtoList(List<LibraryRent> libraryRents) {
        return libraryRents.stream()
                .map(rent -> new LibraryRentDto(rent.getId(), rent.getLibraryBook(), rent.getLibraryUser(),
                        rent.getRentStartDate(), rent.getRentEndDate()))
                .collect(Collectors.toList());
    }
}
