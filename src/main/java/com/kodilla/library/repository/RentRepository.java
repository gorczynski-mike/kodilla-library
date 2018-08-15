package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryRent;
import com.kodilla.library.domain.LibraryUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentRepository extends CrudRepository<LibraryRent, Long> {

    @Override
    List<LibraryRent> findAll();

    @Override
    LibraryRent findOne(Long id);

    List<LibraryRent> findAllByLibraryUser(LibraryUser libraryUser);

    @Override
    LibraryRent save(LibraryRent libraryRent);

    @Override
    void delete(Long id);
}
