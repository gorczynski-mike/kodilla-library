package com.kodilla.library.controller;

import com.kodilla.library.domain.LibraryBook;
import com.kodilla.library.domain.LibraryBookStatus;
import com.kodilla.library.domain.LibraryBookTitle;
import com.kodilla.library.domain.dto.LibraryBookDto;
import com.kodilla.library.domain.dto.LibraryBookTitleDto;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.LibraryDbService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

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

}