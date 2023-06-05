package io.bhimsur.librarian.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book")
@Entity
public class Book implements Serializable {
    private static final long serialVersionUID = -5436516718087586470L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Positive
    private Long id;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "isbn", length = 13)
    private String isbn;

    @Column(name = "stock", length = 5)
    @PositiveOrZero
    private Long stock;

    @Column(name = "book_id", length = 36)
    @NotNull
    private String bookId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modifiedReason")
    private String modifiedReason;
}
