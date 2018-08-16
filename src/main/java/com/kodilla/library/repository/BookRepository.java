package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<LibraryBook, Long> {

    @Override
    LibraryBook save(LibraryBook libraryBook);

    Optional<LibraryBook> findById(Long id);

    @Override
    List<LibraryBook> findAll();

    @Override
    void delete(Long id);
}
