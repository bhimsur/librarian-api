package io.bhimsur.librarian.service.impl;

import io.bhimsur.librarian.dto.BookDto;
import io.bhimsur.librarian.entity.Book;
import io.bhimsur.librarian.exception.GenericException;
import io.bhimsur.librarian.repository.BookRepository;
import io.bhimsur.librarian.repository.SequenceRepository;
import io.bhimsur.librarian.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private SequenceRepository sequenceRepository;

    @Test(expected = GenericException.class)
    public void createBookAlreadyExists() {
        when(bookRepository.findBookByIsbn(anyString()))
                .thenReturn(Optional.of(new Book()));
        bookService.createBook(new BookDto(null, null, "123.444", null));
    }

    @Test
    public void createBookSuccess() {
        when(bookRepository.findBookByIsbn(anyString()))
                .thenReturn(Optional.empty());
        when(sequenceRepository.getSequence(anyString()))
                .thenReturn(new BigDecimal(1).toBigInteger());
        when(bookRepository.save(any()))
                .thenReturn(new Book(1L, "avatar", "123.444", 5L, "BK000000000001", new Date(), null, null));
        BookDto response = bookService.createBook(new BookDto(null, "avatar", "123.444", 5L));
        assertEquals("BK000000000001", response.getBookId());
    }

    @Test(expected = GenericException.class)
    public void getBooksInvalidRequest() {
        bookService.getBooks(true, true);
    }

    @Test
    public void getBooksEmpty() {
        when(bookRepository.findAvailable())
                .thenReturn(new ArrayList<>());
        List<BookDto> response = bookService.getBooks(false, true);
        assertTrue(response.isEmpty());
    }

    @Test
    public void getBooksAll() {
        when(bookRepository.findAll())
                .thenReturn(new ArrayList<>(List.of(new Book())));
        List<BookDto> response = bookService.getBooks(true, false);
        assertEquals(1, response.size());
    }

    @Test
    public void getBooksAvailable() {
        when(bookRepository.findAvailable())
                .thenReturn(new ArrayList<>(List.of(new Book())));
        List<BookDto> response = bookService.getBooks(false, true);
        assertEquals(1, response.size());
    }

    @Test(expected = GenericException.class)
    public void updateBookEmptyBookId() {
        bookService.updateBook(BookDto.builder().bookId("").build());
    }

    @Test
    public void updateBookSuccess() {
        Book book = new Book(1L, "avatar", "122.222", 5L, "BK000000000001", new Date(), null, null);
        when(bookRepository.findByBookId(anyString()))
                .thenReturn(Optional.of(book));
        when(bookRepository.save(any()))
                .thenReturn(book);
        BookDto response = bookService.updateBook(new BookDto("BK000000000001", "avatar", "", 5L));
        assertEquals(book.getBookId(), response.getBookId());
    }
}