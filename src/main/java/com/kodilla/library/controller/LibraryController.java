package com.kodilla.library.controller;

import com.kodilla.library.domain.LibraryBook;
import com.kodilla.library.domain.LibraryBookStatus;
import com.kodilla.library.domain.LibraryBookTitle;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dto.LibraryBookDto;
import com.kodilla.library.domain.dto.LibraryBookTitleDto;
import com.kodilla.library.domain.dto.LibraryUserDto;
import com.kodilla.library.exceptions.BookNotFoundException;
import com.kodilla.library.exceptions.TitleNotFoundException;
import com.kodilla.library.exceptions.UserNotFoundException;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.LibraryDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @GetMapping("/books")
    public List<LibraryBook> getBooks() {
        return libraryDbService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public LibraryBookDto getBook(@PathVariable Long id) {
        try {
            return libraryMapper.mapLibraryBookToLibraryBookDto(libraryDbService.getBook(id));
        } catch (BookNotFoundException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    @GetMapping("/books/byTitleId/{title_id}")
    public List<LibraryBookDto> getBooksByTitleId(@PathVariable Long title_id) {
        try {
            LibraryBookTitle libraryBookTitle = libraryDbService.getTitle(title_id);
            return libraryMapper.mapLibraryBookListToLibraryBookDtoList(libraryDbService.getBooksByTitle(libraryBookTitle));
        } catch (TitleNotFoundException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    @GetMapping("/books/byTitleId/{title_id}/available")
    public List<LibraryBookDto> getBooksByTitleIdAvailable(@PathVariable Long title_id) {
        try {
            LibraryBookTitle libraryBookTitle = libraryDbService.getTitle(title_id);
            return libraryMapper.mapLibraryBookListToLibraryBookDtoList(libraryDbService.getBooksByTitleAvailable(libraryBookTitle));
        } catch (TitleNotFoundException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    @PostMapping("books/createNewBook/forTitleId/{title_id}")
    public void createBook(@PathVariable Long title_id) {
        try {
            LibraryBookTitle libraryBookTitle = libraryDbService.getTitle(title_id);
            LibraryBook newLibraryBook = new LibraryBook(null, libraryBookTitle, LibraryBookStatus.AVAILABLE);
            libraryDbService.saveBook(newLibraryBook);
        } catch (TitleNotFoundException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    @PutMapping("books/{book_id}/changeBookStatus")
    public void changeBookStatus(@PathVariable Long book_id, @RequestParam String newStatus) {
        try {
            LibraryBook libraryBook = libraryDbService.getBook(book_id);
            LibraryBookStatus newStatusEnum = LibraryBookStatus.valueOf(newStatus);
            libraryBook.setLibraryBookStatus(newStatusEnum);
            libraryDbService.saveBook(libraryBook);
        } catch (BookNotFoundException e) {
            LOGGER.warn(e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    @DeleteMapping("books/deleteBook/byId/{id}")
    public void deleteBook(@PathVariable Long id) {
        libraryDbService.deleteBook(id);
    }

}
