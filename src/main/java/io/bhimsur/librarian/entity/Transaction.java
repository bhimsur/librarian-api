package io.bhimsur.librarian.entity;

import io.bhimsur.librarian.constant.TransactionState;
import io.bhimsur.librarian.constant.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction_history")
public class Transaction implements Serializable {
    private static final long serialVersionUID = -7290874472775581498L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive
    @Column(name = "id")
    private Long id;

    @Column(name = "transactionId")
    private String transactionId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "status")
    @Enumerated(value = EnumType.ORDINAL)
    private TransactionStatus status;

    @Column(name = "state")
    @Enumerated(value = EnumType.ORDINAL)
    private TransactionState state;

    @Column(name = "duration_in_day")
    @Positive
    private Integer durationInDay;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_reason")
    private String modifiedReason;
}
