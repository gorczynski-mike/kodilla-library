package com.kodilla.library.controller;

import com.kodilla.library.domain.LibraryBookTitle;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dto.LibraryBookTitleDto;
import com.kodilla.library.domain.dto.LibraryUserDto;
import com.kodilla.library.exceptions.TitleNotFoundException;
import com.kodilla.library.exceptions.UserNotFoundException;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.LibraryDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/library")
@CrossOrigin(origins = "*")
public class LibraryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryController.class);

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
        try {
            return libraryMapper.mapUserToUserDto(libraryDbService.getUser(id));
        } catch (UserNotFoundException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    @PostMapping("/users/createNewUser")
    public void createUser(@RequestBody LibraryUserDto libraryUserDto) {
        libraryUserDto.setId(null);
        libraryUserDto.setAccountCreatedDate(LocalDate.now());
        libraryDbService.saveUser(libraryMapper.mapUserDtoToUser(libraryUserDto));
    }

    @DeleteMapping("/users/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        libraryDbService.deleteUser(id);
    }

    @GetMapping("/titles")
    public List<LibraryBookTitle> getTitles() {
        return libraryDbService.getAllTitles();
    }

    @GetMapping("/titles/{id}")
    public LibraryBookTitleDto getTitle(@PathVariable Long id) {
        try {
            return libraryMapper.mapBookTitleToBookTitleDto(libraryDbService.getTitle(id));
        } catch (TitleNotFoundException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    @PostMapping("/titles/createNewTitle")
    public void createTitle(@RequestBody LibraryBookTitleDto libraryBookTitleDto) {
        libraryBookTitleDto.setId(null);
        libraryDbService.saveTitle(libraryMapper.mapBookTitleDtoToBookTitle(libraryBookTitleDto));
    }

    @DeleteMapping("/titles/deleteTitle/{id}")
    public void deleteTitle(@PathVariable Long id) {
        libraryDbService.deleteTitle(id);
    }

}
