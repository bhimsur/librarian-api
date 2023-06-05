package io.bhimsur.librarian.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto implements Serializable {
    private static final long serialVersionUID = -842346353459133533L;
    private String transactionId;
    private String memberId;
    private String memberName;
    private String bookId;
    private String bookName;
    private Long quantity;
    private String status;
    private Integer duration;
    private Boolean isLate;
    private Date transactionDate;
    private Date returnDate;
    private String state;
}
