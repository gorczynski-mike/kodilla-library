package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<LibraryUser, Long> {

    @Override
    List<LibraryUser> findAll();

    Optional<LibraryUser> findById(Long id);

    @Override
    LibraryUser save(LibraryUser libraryUser);

    @Override
    void delete(Long aLong);
}
