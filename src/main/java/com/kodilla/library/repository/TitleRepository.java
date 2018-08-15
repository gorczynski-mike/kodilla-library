package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryBookTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TitleRepository extends CrudRepository<LibraryBookTitle, Long> {

    @Override
    List<LibraryBookTitle> findAll();

    @Override
    LibraryBookTitle findOne(Long id);

    LibraryBookTitle findDistinctByTitle(String title);

    List<LibraryBookTitle> findAllByAuthor(String author);

    List<LibraryBookTitle> findAllByPublicationYear(int publicationYear);

    @Override
    LibraryBookTitle save(LibraryBookTitle libraryBookTitle);

    @Override
    void delete(Long id);
}
