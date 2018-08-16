package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryBook;
import com.kodilla.library.domain.LibraryBookStatus;
import com.kodilla.library.domain.LibraryBookTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<LibraryBook, Long> {

    @Override
    LibraryBook save(LibraryBook libraryBook);

    Optional<LibraryBook> findById(Long id);

    List<LibraryBook> findAllByLibraryBookTitle(LibraryBookTitle libraryBookTitle);

    List<LibraryBook> findAllByLibraryBookTitleAndLibraryBookStatus(LibraryBookTitle libraryBookTitle,
                                                                    LibraryBookStatus libraryBookStatus);

    @Override
    List<LibraryBook> findAll();

    @Override
    void delete(Long id);
}
