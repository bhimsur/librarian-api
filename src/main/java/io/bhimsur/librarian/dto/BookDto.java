package io.bhimsur.librarian.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto implements Serializable {
    private static final long serialVersionUID = -8053938374344320466L;
    private String bookId;
    private String bookName;
    private String isbn;
    private Long stock;
}
