package com.kodilla.library.controller;

import com.kodilla.library.domain.LibraryBookTitle;
import com.kodilla.library.exceptions.TitleNotFoundException;
import com.kodilla.library.service.LibraryDbService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryDbServiceTestSuite {

    @Autowired
    LibraryDbService libraryDbService;

    @Test
    @Transactional
    public void shouldCreateNewTitle() throws TitleNotFoundException {
        //Given
        Assert.assertEquals(0, libraryDbService.getAllTitlesByTitle("test_title").size());
        LibraryBookTitle title1 = new LibraryBookTitle(null, "test_title", "test_author", 1970);

        //When
        libraryDbService.saveTitle(title1);

        //Then
        List<LibraryBookTitle> resultTitles = libraryDbService.getAllTitlesByTitle("test_title");
        Assert.assertEquals(1, resultTitles.size());
        Assert.assertEquals("test_title", resultTitles.get(0).getTitle());
        Assert.assertEquals("test_author", resultTitles.get(0).getAuthor());
        Assert.assertEquals(1970, resultTitles.get(0).getPublicationYear());

        //Cleanup
        resultTitles.forEach(title -> libraryDbService.deleteTitle(title.getId()));
    }

    @Test
    public void shouldNotCreateExistingTitleAgain() throws TitleNotFoundException {
        //Given
        Assert.assertEquals(0, libraryDbService.getAllTitlesByTitle("test_title").size());
        LibraryBookTitle title1 = new LibraryBookTitle(null, "test_title", "test_author", 1970);
        LibraryBookTitle title2 = new LibraryBookTitle(null, "test_title", "test_author", 1970);
        boolean exceptionWasThrown = false;

        //When
        try {
            libraryDbService.saveTitle(title1);
            libraryDbService.saveTitle(title2);
        } catch (DataIntegrityViolationException e) {
            exceptionWasThrown = true;
        }

        //Then
        List<LibraryBookTitle> resultTitles = libraryDbService.getAllTitlesByTitle("test_title");
        Assert.assertEquals(1, resultTitles.size());
        Assert.assertEquals("test_title", resultTitles.get(0).getTitle());
        Assert.assertEquals("test_author", resultTitles.get(0).getAuthor());
        Assert.assertEquals(1970, resultTitles.get(0).getPublicationYear());
        Assert.assertTrue(exceptionWasThrown);

        //Cleanup
        resultTitles.forEach(title -> libraryDbService.deleteTitle(title.getId()));
    }

}
