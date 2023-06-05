package io.bhimsur.librarian.service.impl;

import io.bhimsur.librarian.constant.Constant;
import io.bhimsur.librarian.constant.ErrorCode;
import io.bhimsur.librarian.dto.BookDto;
import io.bhimsur.librarian.entity.Book;
import io.bhimsur.librarian.exception.GenericException;
import io.bhimsur.librarian.repository.BookRepository;
import io.bhimsur.librarian.repository.SequenceRepository;
import io.bhimsur.librarian.service.BookService;
import io.bhimsur.librarian.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final SequenceRepository sequenceRepository;

    @Override
    public BookDto createBook(BookDto request) {
        Optional<Book> bookByIsbn = bookRepository.findBookByIsbn(request.getIsbn());
        if (bookByIsbn.isPresent()) {
            throw new GenericException(ErrorCode.BOOK_ALREADY_EXISTS);
        }
        Book book = bookRepository.save(Book.builder()
                .bookId(RandomUtil.getSequence(Constant.Prefix.BOOK_PREFIX, sequenceRepository.getSequence(Constant.Sequence.BOOK_SEQ)))
                .name(request.getBookName())
                .isbn(request.getIsbn())
                .stock(request.getStock())
                .createdDate(new Date())
                .build());

        return BookDto.builder()
                .bookId(book.getBookId())
                .stock(book.getStock())
                .bookName(book.getName())
                .isbn(book.getIsbn())
                .build();
    }

    @Override
    public List<BookDto> getBooks(boolean isAll, boolean isAvailable) {
        List<Book> books;
        if (isAll && !isAvailable) {
            books = bookRepository.findAll();
        } else if (!isAll && isAvailable) {
            books = bookRepository.findAvailable();
        } else {
            throw new GenericException(ErrorCode.INVALID_REQUEST);
        }

        if (books.isEmpty()) {
            return new ArrayList<>();
        } else {
            return books.stream()
                    .map(book -> BookDto.builder()
                            .bookId(book.getBookId())
                            .bookName(book.getName())
                            .isbn(book.getIsbn())
                            .stock(book.getStock())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public BookDto updateBook(BookDto request) {
        if (StringUtils.isEmpty(request.getBookId())) {
            throw new GenericException(ErrorCode.INVALID_REQUEST);
        }
        Book book = bookRepository.findByBookId(request.getBookId()).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
        if (request.getStock() != null) {
            book.setStock(request.getStock());
        }
        if (!StringUtils.isEmpty(request.getBookName())) {
            book.setName(request.getBookName());
        }

        book = bookRepository.save(book);
        return BookDto.builder()
                .bookId(book.getBookId())
                .bookName(book.getName())
                .isbn(book.getIsbn())
                .stock(book.getStock())
                .build();
    }
}
