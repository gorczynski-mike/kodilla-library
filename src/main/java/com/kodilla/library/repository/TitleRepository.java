package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TitleRepository extends CrudRepository<LibraryTitle, Long> {

    @Override
    List<LibraryTitle> findAll();

    @Override
    LibraryTitle findOne(Long id);

    LibraryTitle findDistinctByTitle(String title);

    List<LibraryTitle> findAllByAuthor(String author);

    List<LibraryTitle> findAllByPublicationYear(int publicationYear);

    @Override
    LibraryTitle save(LibraryTitle libraryTitle);

    @Override
    void delete(Long id);
}
