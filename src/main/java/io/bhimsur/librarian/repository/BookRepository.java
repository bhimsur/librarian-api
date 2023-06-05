package io.bhimsur.librarian.repository;

import io.bhimsur.librarian.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM book b WHERE b.stock >= 1",
            nativeQuery = true)
    List<Book> findAvailable();

    @Query(value = "SELECT * FROM book b WHERE b.isbn = ?1",
            nativeQuery = true)
    Optional<Book> findBookByIsbn(String isbn);

    Optional<Book> findByBookId(String bookId);
}
