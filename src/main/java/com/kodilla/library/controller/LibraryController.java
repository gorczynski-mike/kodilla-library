package com.kodilla.library.controller;

import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dto.LibraryUserDto;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.LibraryDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/library")
@CrossOrigin(origins = "*")
public class LibraryController {

    @Autowired
    LibraryDbService libraryDbService;
    @Autowired
    LibraryMapper libraryMapper;

    @GetMapping("/users")
    public List<LibraryUser> getUsers() {
        return libraryDbService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public LibraryUserDto getUser(@PathVariable Long id) {
        return libraryMapper.mapUserToUserDto(libraryDbService.getUser(id));
    }

    @PostMapping("/users/createNewUser")
    public void createUser(@RequestBody LibraryUserDto libraryUserDto) {
        libraryUserDto.setId(null);
        libraryUserDto.setAccountCreatedDate(LocalDate.now());
        libraryDbService.saveUser(libraryMapper.mapUserDtoToUser(libraryUserDto));
    }

}
