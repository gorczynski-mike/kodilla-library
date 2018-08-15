package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<LibraryUser, Long> {

    @Override
    List<LibraryUser> findAll();

    @Override
    LibraryUser findOne(Long id);

    @Override
    LibraryUser save(LibraryUser libraryUser);

    @Override
    void delete(Long aLong);
}
