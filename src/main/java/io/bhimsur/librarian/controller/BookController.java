package io.bhimsur.librarian.controller;

import io.bhimsur.librarian.annotation.AdminPermit;
import io.bhimsur.librarian.dto.BaseResponse;
import io.bhimsur.librarian.dto.BookDto;
import io.bhimsur.librarian.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @AdminPermit
    @PostMapping
    public ResponseEntity<BaseResponse<BookDto>> createBook(@RequestBody BookDto request) {
        return new ResponseEntity<>(new BaseResponse<>(bookService.createBook(request)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<ArrayList<BookDto>>> getBooks(@RequestParam(required = false, name = "is_all") boolean isAll,
                                                                     @RequestParam(required = false, name = "is_available") boolean isAvailable) {
        return new ResponseEntity<>(new BaseResponse<>(new ArrayList<>(bookService.getBooks(isAll, isAvailable))), HttpStatus.OK);
    }

    @PutMapping
    @AdminPermit
    public ResponseEntity<BaseResponse<BookDto>> updateBook(@RequestBody BookDto request) {
        return new ResponseEntity<>(new BaseResponse<>(bookService.updateBook(request)), HttpStatus.OK);
    }
}
