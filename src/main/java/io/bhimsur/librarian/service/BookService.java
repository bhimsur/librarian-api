package io.bhimsur.librarian.service;

import io.bhimsur.librarian.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto request);
    List<BookDto> getBooks(boolean isAll, boolean isAvailable);
    BookDto updateBook(BookDto request);
}
