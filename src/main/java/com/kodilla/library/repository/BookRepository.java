package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<LibraryBook, Long> {

    @Override
    LibraryBook save(LibraryBook libraryBook);

    @Override
    LibraryBook findOne(Long id);

    @Override
    List<LibraryBook> findAll();

    @Override
    void delete(Long id);
}
