package com.kodilla.library.service;

import com.kodilla.library.domain.*;
import com.kodilla.library.exceptions.BookNotFoundException;
import com.kodilla.library.exceptions.RentNotFoundException;
import com.kodilla.library.exceptions.TitleNotFoundException;
import com.kodilla.library.exceptions.UserNotFoundException;
import com.kodilla.library.repository.BookRepository;
import com.kodilla.library.repository.RentRepository;
import com.kodilla.library.repository.TitleRepository;
import com.kodilla.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryDbService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TitleRepository titleRepository;
    @Autowired
    RentRepository rentRepository;

    public List<LibraryUser> getAllUsers() {
        return userRepository.findAll();
    }

    public LibraryUser getUser(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(UserNotFoundException.USER_NOT_FOUND_EXCEPTION + " for user_id: " + id));
    }

    public boolean userExists(Long id) throws UserNotFoundException {
        if(userRepository.exists(id)) {
            return true;
        } else {
            throw new UserNotFoundException(UserNotFoundException.USER_NOT_FOUND_EXCEPTION + " for user_id: " + id);
        }
    }

    public void saveUser(LibraryUser libraryUser) {
        userRepository.save(libraryUser);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public List<LibraryBookTitle> getAllTitles() {
        return titleRepository.findAll();
    }

    public LibraryBookTitle getTitle(Long id) throws TitleNotFoundException {
        return titleRepository.findById(id).orElseThrow(() ->
                new TitleNotFoundException(TitleNotFoundException.TITLE_NOT_FOUND_EXCEPTION + " for title_id: " + id));
    }

    public void saveTitle(LibraryBookTitle libraryBookTitle) {
        titleRepository.save(libraryBookTitle);
    }

    public void deleteTitle(Long id) {
        titleRepository.delete(id);
    }

    public List<LibraryBook> getAllBooks() {
        return bookRepository.findAll();
    }

    public LibraryBook getBook(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException(BookNotFoundException.BOOK_NOT_FOUND_EXCEPTION + " for book_id: " + id));
    }

    public List<LibraryBook> getBooksByTitle(LibraryBookTitle libraryBookTitle) {
        return bookRepository.findAllByLibraryBookTitle(libraryBookTitle);
    }

    public List<LibraryBook> getBooksByTitleAvailable(LibraryBookTitle libraryBookTitle) {
        return bookRepository.findAllByLibraryBookTitleAndLibraryBookStatus(libraryBookTitle, LibraryBookStatus.AVAILABLE);
    }

    public void saveBook(LibraryBook libraryBook) {
        bookRepository.save(libraryBook);
    }

    public void saveOrUpdateBook(LibraryBook libraryBook) {
        bookRepository.save(libraryBook);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    public LibraryRent getRent(Long id) throws RentNotFoundException {
        return rentRepository.findById(id).orElseThrow(() ->
                new RentNotFoundException(RentNotFoundException.RENT_NOT_FOUND_EXCEPTION + " for rent_id: " + id));
    }

    public List<LibraryRent> getAllRents() {
        return rentRepository.findAll();
    }

    public void saveRent(LibraryRent libraryRent) {
        rentRepository.save(libraryRent);
    }
}
