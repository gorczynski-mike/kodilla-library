package com.kodilla.library.service;

import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.exceptions.UserNotFoundException;
import com.kodilla.library.repository.BookRepository;
import com.kodilla.library.repository.RentRepository;
import com.kodilla.library.repository.TitleRepository;
import com.kodilla.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;

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
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void saveUser(LibraryUser libraryUser) {
        userRepository.save(libraryUser);
    }
}
