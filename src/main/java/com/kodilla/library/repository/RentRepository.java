package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryRent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RentRepository extends CrudRepository<LibraryRent, Long> {

    @Override
    List<LibraryRent> findAll();

    @Override
    LibraryRent findOne(Long id);

    Optional<LibraryRent> findById(Long id);

    @Override
    LibraryRent save(LibraryRent libraryRent);

    @Override
    void delete(Long id);
}
