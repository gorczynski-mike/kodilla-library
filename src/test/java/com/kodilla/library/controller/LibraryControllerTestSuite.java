package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dto.LibraryBookDto;
import com.kodilla.library.domain.dto.LibraryBookTitleDto;
import com.kodilla.library.exceptions.BookNotAvailableException;
import com.kodilla.library.exceptions.CantEndRentWhenBookIsLost;
import com.kodilla.library.exceptions.RentAlreadyEndedException;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.LibraryDbService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
public class LibraryControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryDbService libraryDbService;

    @MockBean
    private LibraryMapper libraryMapper;

    @Test
    public void shouldFetchEmptyBooksList() throws Exception {
        //Given
        List<LibraryBook> libraryBooks = new ArrayList<>();
        List<LibraryBookDto> libraryBooksDto = new ArrayList<>();
        when(libraryDbService.getAllBooks()).thenReturn(libraryBooks);
        when(libraryMapper.mapLibraryBookListToLibraryBookDtoList(libraryBooks)).thenReturn(libraryBooksDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/library/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldFetchNotEmptyBooksList() throws Exception {
        //Given
        LibraryBook libraryBook = new LibraryBook(1L, new LibraryBookTitle(1L, "test_title", "test_author", 1970),
                LibraryBookStatus.AVAILABLE);
        List<LibraryBook> libraryBooks = new ArrayList<>();
        libraryBooks.add(libraryBook);
        LibraryBookDto libraryBookDto = new LibraryBookDto(1L, new LibraryBookTitleDto(1L, "test_title", "test_author", 1970),
                LibraryBookStatus.AVAILABLE);
        List<LibraryBookDto> libraryBooksDto = new ArrayList<>();
        libraryBooksDto.add(libraryBookDto);

        when(libraryDbService.getAllBooks()).thenReturn(libraryBooks);
        when(libraryMapper.mapLibraryBookListToLibraryBookDtoList(libraryBooks)).thenReturn(libraryBooksDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/library/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].libraryBookTitle.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].libraryBookTitle.title", Matchers.is("test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].libraryBookTitle.author", Matchers.is("test_author")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].libraryBookTitle.publicationYear", Matchers.is(1970)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].libraryBookStatus", Matchers.is("AVAILABLE")));
    }

    @Test
    public void shouldRentAvailableBookAndChangeBookStatus() throws Exception {
        //Given
        Long user_id = 1L;
        Long book_id = 1L;
        LibraryUser user = new LibraryUser(user_id, "firstname", "lastname", LocalDate.now());
        LibraryBook book = new LibraryBook(book_id, new LibraryBookTitle(1L, "title", "author", 1970),
                LibraryBookStatus.AVAILABLE);

        ArgumentCaptor<LibraryRent> rentCaptor = ArgumentCaptor.forClass(LibraryRent.class);

        when(libraryDbService.getUser(user_id)).thenReturn(user);
        when(libraryDbService.getBook(book_id)).thenReturn(book);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/library/rents/createNewRent")
                .param("user_id", String.valueOf(user_id))
                .param("book_id", String.valueOf(book_id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));


        //Then
        verify(libraryDbService).saveRent(rentCaptor.capture());
        LibraryRent createdRent = rentCaptor.getValue();
        Assert.assertNotNull(createdRent);
        Assert.assertEquals(book, createdRent.getLibraryBook());
        Assert.assertEquals(user, createdRent.getLibraryUser());
        Assert.assertEquals(LibraryBookStatus.RENTED, book.getLibraryBookStatus());
        Assert.assertEquals(LocalDate.now(), createdRent.getRentStartDate());
        Assert.assertNull(createdRent.getRentEndDate());

    }

    @Test
    public void shouldNotRentBookNotAvailable() throws Exception {
        //Given
        Long user_id = 1L;
        Long book_id = 1L;
        LibraryUser user = new LibraryUser(user_id, "firstname", "lastname", LocalDate.now());
        LibraryBook book = new LibraryBook(book_id, new LibraryBookTitle(1L, "title", "author", 1970),
                LibraryBookStatus.RENTED);
        boolean exceptionWasThrown = false;
        Class<?> exceptionClass = null;

        ArgumentCaptor<LibraryRent> rentCaptor = ArgumentCaptor.forClass(LibraryRent.class);

        when(libraryDbService.getUser(user_id)).thenReturn(user);
        when(libraryDbService.getBook(book_id)).thenReturn(book);

        //When
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/v1/library/rents/createNewRent")
                    .param("user_id", String.valueOf(user_id))
                    .param("book_id", String.valueOf(book_id))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        } catch (Exception e) {
            exceptionWasThrown = true;
            exceptionClass = e.getCause().getClass();
        }


        //Then
        Assert.assertTrue(exceptionWasThrown);
        Assert.assertEquals(BookNotAvailableException.class, exceptionClass);
    }

    @Test
    public void shouldNotEndRentWhenBookIsLost() throws Exception {
        //Given
        Long user_id = 1L;
        Long book_id = 1L;
        LibraryUser user = new LibraryUser(user_id, "firstname", "lastname", LocalDate.now());
        LibraryBook book = new LibraryBook(book_id, new LibraryBookTitle(1L, "title", "author", 1970),
                LibraryBookStatus.LOST);
        LibraryRent rent = new LibraryRent(1L, book, user, LocalDate.now(), null);
        boolean exceptionWasThrown = false;
        Class<?> exceptionClass = null;

        when(libraryDbService.getRent(1L)).thenReturn(rent);

        //When
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/v1/library/rents/endRent/byId/{rent_id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        } catch (Exception e) {
            exceptionWasThrown = true;
            exceptionClass = e.getCause().getClass();
        }

        //Then
        Assert.assertTrue(exceptionWasThrown);
        Assert.assertEquals(CantEndRentWhenBookIsLost.class, exceptionClass);
    }

    @Test
    public void shouldNotEndRentAlreadyEnded() throws Exception {
        //Given
        Long user_id = 1L;
        Long book_id = 1L;
        LibraryUser user = new LibraryUser(user_id, "firstname", "lastname", LocalDate.now());
        LibraryBook book = new LibraryBook(book_id, new LibraryBookTitle(1L, "title", "author", 1970),
                LibraryBookStatus.AVAILABLE);
        LibraryRent rent = new LibraryRent(1L, book, user, LocalDate.now().minusDays(5), LocalDate.now());
        boolean exceptionWasThrown = false;
        Class<?> exceptionClass = null;

        when(libraryDbService.getRent(1L)).thenReturn(rent);

        //When
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/v1/library/rents/endRent/byId/{rent_id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        } catch (Exception e) {
            exceptionWasThrown = true;
            exceptionClass = e.getCause().getClass();
        }

        //Then
        Assert.assertTrue(exceptionWasThrown);
        Assert.assertEquals(RentAlreadyEndedException.class, exceptionClass);
    }

    @Test
    public void shouldBeAbleToPayForLostBookAndEndRent() throws Exception {
        //Given
        Long user_id = 1L;
        Long book_id = 1L;
        LibraryUser user = new LibraryUser(user_id, "firstname", "lastname", LocalDate.now());
        LibraryBook book = new LibraryBook(book_id, new LibraryBookTitle(1L, "title", "author", 1970),
                LibraryBookStatus.LOST);
        LibraryRent rent = new LibraryRent(1L, book, user, LocalDate.now(), null);

        when(libraryDbService.getRent(1L)).thenReturn(rent);

        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/library/rents/payForLostRent/byId/{rent_id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().is(200));

        //Then
        Assert.assertEquals(LibraryBookStatus.AVAILABLE, book.getLibraryBookStatus());
        Assert.assertNotNull(rent.getRentEndDate());
    }

}