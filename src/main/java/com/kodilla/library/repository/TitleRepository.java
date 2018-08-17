package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryBookTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends CrudRepository<LibraryBookTitle, Long> {

    @Override
    List<LibraryBookTitle> findAll();

    Optional<LibraryBookTitle> findById(Long id);

    LibraryBookTitle findDistinctByTitle(String title);

    List<LibraryBookTitle> findAllByAuthor(String author);

    List<LibraryBookTitle> findAllByTitle(String title);

    List<LibraryBookTitle> findAllByPublicationYear(int publicationYear);

    @Override
    LibraryBookTitle save(LibraryBookTitle libraryBookTitle);

    @Override
    void delete(Long id);
}
