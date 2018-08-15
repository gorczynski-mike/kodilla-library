package com.kodilla.library.mapper;

import com.kodilla.library.domain.LibraryUser;
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

}
