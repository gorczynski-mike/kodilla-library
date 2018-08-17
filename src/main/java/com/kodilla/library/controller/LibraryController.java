package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dto.LibraryBookDto;
import com.kodilla.library.domain.dto.LibraryBookTitleDto;
import com.kodilla.library.domain.dto.LibraryRentDto;
import com.kodilla.library.domain.dto.LibraryUserDto;
import com.kodilla.library.exceptions.*;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.LibraryDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
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
    public void createTitle(@RequestBody LibraryBookTitleDto libraryBookTitleDto) throws TitleAlreadyExistsException {
        libraryBookTitleDto.setId(null);
        try {
            libraryDbService.saveTitle(libraryMapper.mapBookTitleDtoToBookTitle(libraryBookTitleDto));
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn(e.getMessage());
            throw new TitleAlreadyExistsException();
        }
    }

    @DeleteMapping("/titles/deleteTitle/{id}")
    public void deleteTitle(@PathVariable Long id) {
        libraryDbService.deleteTitle(id);
    }

    @GetMapping("/books")
    public List<LibraryBookDto> getBooks() {
        return libraryMapper.mapLibraryBookListToLibraryBookDtoList(libraryDbService.getAllBooks());
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
    public void createBook(@PathVariable Long title_id) throws GenericLibraryException {
        try {
            LibraryBookTitle libraryBookTitle = libraryDbService.getTitle(title_id);
            LibraryBook newLibraryBook = new LibraryBook(null, libraryBookTitle, LibraryBookStatus.AVAILABLE);
            libraryDbService.saveBook(newLibraryBook);
        } catch (TitleNotFoundException e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    @PutMapping("books/{book_id}/changeBookStatus")
    public void changeBookStatus(@PathVariable Long book_id, @RequestParam String newStatus) throws GenericLibraryException {
        try {
            LibraryBook libraryBook = libraryDbService.getBook(book_id);
            LibraryBookStatus newStatusEnum = LibraryBookStatus.valueOf(newStatus);
            libraryBook.setLibraryBookStatus(newStatusEnum);
            libraryDbService.saveBook(libraryBook);
        } catch (BookNotFoundException e) {
            LOGGER.warn(e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            GenericLibraryException exception =
                    new BookStatusNotExistsException(BookStatusNotExistsException.BOOK_STATUS_NOT_EXISTS_EXCEPTION +
                    " for status: " + newStatus);
            LOGGER.warn(exception.getMessage());
            throw exception;
        }
    }

    @DeleteMapping("books/deleteBook/byId/{id}")
    public void deleteBook(@PathVariable Long id) {
        libraryDbService.deleteBook(id);
    }

    @GetMapping("rents")
    public List<LibraryRentDto> getAllRents() {
        return libraryMapper.mapLibraryRentListToLibraryRentDtoList(libraryDbService.getAllRents());
    }

    @Transactional
    @PostMapping("rents/createNewRent")
    public void createNewRent(@RequestParam Long user_id, @RequestParam Long book_id) throws GenericLibraryException {
        try {
            LibraryUser libraryUser = libraryDbService.getUser(user_id);
            LibraryBook libraryBook = libraryDbService.getBook(book_id);
            if(!libraryBook.getLibraryBookStatus().equals(LibraryBookStatus.AVAILABLE)) {
                throw new BookNotAvailableException(BookNotAvailableException.BOOK_NOT_AVAILABLE_EXCEPTION + " for book_id: " + book_id);
            }
            libraryBook.setLibraryBookStatus(LibraryBookStatus.RENTED);
            LibraryRent libraryRent = new LibraryRent(null, libraryBook, libraryUser, LocalDate.now(), null);
            libraryDbService.saveRent(libraryRent);
        } catch (UserNotFoundException | BookNotFoundException | BookNotAvailableException e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    @Transactional
    @PutMapping("rents/endRent/byId/{rent_id}")
    public void endRent(@PathVariable Long rent_id) throws GenericLibraryException {
        try {
            LibraryRent libraryRent = libraryDbService.getRent(rent_id);
            if(libraryRent.getRentEndDate() != null) {
                throw new RentAlreadyEndedException(RentAlreadyEndedException.RENT_ALREADY_ENDED_EXCEPTION + " for rent_id: " + rent_id);
            }
            if(libraryRent.getLibraryBook().getLibraryBookStatus().equals(LibraryBookStatus.LOST)) {
                throw new CantEndRentWhenBookIsLost(CantEndRentWhenBookIsLost.CANT_END_RENT_WHEN_BOOK_LOST_EXCEPTION + " for rent id: " + rent_id);
            }
            libraryRent.setRentEndDate(LocalDate.now());
            libraryRent.getLibraryBook().setLibraryBookStatus(LibraryBookStatus.AVAILABLE);
            libraryDbService.saveRent(libraryRent);
        } catch (RentNotFoundException | RentAlreadyEndedException | CantEndRentWhenBookIsLost e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    @Transactional
    @PutMapping("rents/payForLostRent/byId/{rent_id}")
    public void payForLostRent(@PathVariable Long rent_id) throws GenericLibraryException {
        try {
            LibraryRent libraryRent = libraryDbService.getRent(rent_id);
            if(libraryRent.getRentEndDate() != null) {
                throw new RentAlreadyEndedException(RentAlreadyEndedException.RENT_ALREADY_ENDED_EXCEPTION + " for rent_id: " + rent_id);
            }
            if(!libraryRent.getLibraryBook().getLibraryBookStatus().equals(LibraryBookStatus.LOST)) {
                throw new CantPayForBookNotLost(CantPayForBookNotLost.CANT_PAY_FOR_BOOK_NOT_LOST_EXCEPTION + " for rent id: " + rent_id);
            }
            libraryRent.setRentEndDate(LocalDate.now());
            libraryRent.getLibraryBook().setLibraryBookStatus(LibraryBookStatus.AVAILABLE);
            libraryDbService.saveRent(libraryRent);
        } catch (RentNotFoundException | RentAlreadyEndedException | CantPayForBookNotLost e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

}
