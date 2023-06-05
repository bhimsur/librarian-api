package io.bhimsur.librarian.service.impl;

import io.bhimsur.librarian.constant.TransactionState;
import io.bhimsur.librarian.constant.TransactionStatus;
import io.bhimsur.librarian.dto.TransactionDto;
import io.bhimsur.librarian.entity.Book;
import io.bhimsur.librarian.entity.Member;
import io.bhimsur.librarian.entity.Transaction;
import io.bhimsur.librarian.exception.GenericException;
import io.bhimsur.librarian.repository.BookRepository;
import io.bhimsur.librarian.repository.MemberRepository;
import io.bhimsur.librarian.repository.SequenceRepository;
import io.bhimsur.librarian.repository.TransactionRepository;
import io.bhimsur.librarian.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TransactionServiceImpl.class)
public class TransactionServiceImplTest {

    @Autowired
    private TransactionService transactionService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private SequenceRepository sequenceRepository;

    @Test(expected = GenericException.class)
    public void inquiryDataNotFound() {
        when(bookRepository.findByBookId(anyString()))
                .thenReturn(Optional.empty());
        transactionService.inquiry(TransactionDto.builder().bookId("BK000000000001").build());
    }

    @Test(expected = GenericException.class)
    public void inquiryOutOfStock() {
        when(bookRepository.findByBookId(anyString()))
                .thenReturn(Optional.of(Book.builder()
                        .stock(0L)
                        .build()));
        transactionService.inquiry(TransactionDto.builder().bookId("BK000000000001").quantity(2L).build());
    }

    @Test(expected = GenericException.class)
    public void inquiryMemberNotFound() {
        when(bookRepository.findByBookId(anyString()))
                .thenReturn(Optional.of(Book.builder()
                        .stock(5L)
                        .build()));
        when(memberRepository.findByMemberId(anyString()))
                .thenReturn(Optional.empty());
        transactionService.inquiry(TransactionDto.builder()
                .bookId("BK000000000001")
                .quantity(2L)
                .memberId("ME000000000003")
                .build());
    }

    @Test
    public void inquirySuccess() {
        when(bookRepository.findByBookId(anyString()))
                .thenReturn(Optional.of(Book.builder()
                        .stock(5L)
                        .build()));
        when(memberRepository.findByMemberId(anyString()))
                .thenReturn(Optional.of(Member.builder().id(1L).memberId("ME000000000003").build()));
        when(sequenceRepository.getSequence(anyString()))
                .thenReturn(new BigDecimal(3).toBigInteger());
        when(transactionRepository.save(any()))
                .thenReturn(Transaction.builder()
                        .transactionId("TR000000000004")
                        .build());
        TransactionDto response = transactionService.inquiry(TransactionDto.builder()
                .bookId("BK000000000001")
                .quantity(2L)
                .memberId("ME000000000003")
                .build());
        assertEquals("TR000000000004", response.getTransactionId());
    }

    @Test(expected = GenericException.class)
    public void executeAlreadyExecuted() {
        when(transactionRepository.findByTransactionId(anyString()))
                .thenReturn(Optional.of(Transaction.builder()
                        .state(TransactionState.EXECUTE)
                        .build()));
        transactionService.execute(TransactionDto.builder()
                .transactionId("TR000000000004")
                .build());
    }

    @Test(expected = GenericException.class)
    public void executeOutOfStock() {
        when(transactionRepository.findByTransactionId(anyString()))
                .thenReturn(Optional.of(Transaction.builder()
                        .state(TransactionState.INQUIRY)
                        .quantity(2L)
                        .bookId(1L)
                        .build()));
        when(bookRepository.findById(any()))
                .thenReturn(Optional.of(Book.builder()
                        .stock(1L)
                        .build()));
        transactionService.execute(TransactionDto.builder()
                .transactionId("TR000000000004")
                .build());
    }

    @Test
    public void executeSuccess() {
        when(transactionRepository.findByTransactionId(anyString()))
                .thenReturn(Optional.of(Transaction.builder()
                        .state(TransactionState.INQUIRY)
                        .quantity(1L)
                        .bookId(1L)
                        .build()));
        when(bookRepository.findById(any()))
                .thenReturn(Optional.of(Book.builder()
                        .stock(1L)
                        .build()));
        when(bookRepository.save(any()))
                .thenReturn(Book.builder().name("avatar").build());
        when(transactionRepository.save(any()))
                .thenReturn(Transaction.builder()
                        .status(TransactionStatus.SUCCESS)
                        .transactionId("TR000000000004")
                        .quantity(1L)
                        .build());
        TransactionDto response = transactionService.execute(TransactionDto.builder()
                .transactionId("TR000000000004")
                .build());
        assertEquals(TransactionStatus.SUCCESS.name(), response.getStatus());
    }

    @Test
    public void getTransactionsEmpty() {
        when(transactionRepository.findAll())
                .thenReturn(Collections.emptyList());
        List<TransactionDto> response = transactionService.getTransactions(null);
        assertTrue(response.isEmpty());
    }

    @Test
    public void getTransactionsByState() {
        when(transactionRepository.findByState(any()))
                .thenReturn(Collections.emptyList());
        List<TransactionDto> response = transactionService.getTransactions(TransactionState.INQUIRY.name());
        assertTrue(response.isEmpty());
    }

    @Test
    public void getTransactionsSuccess() {
        when(transactionRepository.findByState(any()))
                .thenReturn(new ArrayList<>(List.of(Transaction.builder()
                        .transactionDate(new Date())
                        .durationInDay(4)
                        .returnDate(new Date())
                        .memberId(1L)
                        .bookId(1L)
                        .status(TransactionStatus.RETURNED)
                        .state(TransactionState.EXECUTE)
                        .build())));
        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(Member.builder()
                                .memberId("ME122")
                                .name("member")
                        .build()));
        when(bookRepository.findById(any()))
                .thenReturn(Optional.of(Book.builder()
                                .bookId("BO111")
                                .name("book")
                        .build()));
        List<TransactionDto> response = transactionService.getTransactions(TransactionState.EXECUTE.name());
        assertEquals(1, response.size());
    }

    @Test(expected = GenericException.class)
    public void returnedInvalidTransaction() {
        when(transactionRepository.findByTransactionId(anyString()))
                .thenReturn(Optional.of(Transaction.builder().state(TransactionState.INQUIRY).build()));
        transactionService.returned(TransactionDto.builder().transactionId("TR001").build());
    }

    @Test
    public void returnedSuccess() {
        when(transactionRepository.findByTransactionId(anyString()))
                .thenReturn(Optional.of(Transaction.builder()
                        .bookId(1L)
                        .durationInDay(1)
                        .transactionDate(new Date())
                        .build()));
        when(bookRepository.findById(any()))
                .thenReturn(Optional.of(Book.builder()
                        .stock(5L)
                        .build()));
        when(transactionRepository.save(any()))
                .thenReturn(Transaction.builder()
                        .quantity(5L)
                        .status(TransactionStatus.RETURNED)
                        .durationInDay(1)
                        .transactionDate(new Date())
                        .build());
        when(bookRepository.save(any()))
                .thenReturn(Book.builder()
                        .name("book")
                        .stock(5L)
                        .build());
        TransactionDto response = transactionService.returned(TransactionDto.builder()
                .returnDate(new Date())
                .transactionId("TR0001")
                .status(TransactionStatus.RETURNED.name())
                .build());
        assertEquals(TransactionStatus.RETURNED.name(), response.getStatus());
    }

    @Test
    public void getTransactionSuccess() {
        when(transactionRepository.findByTransactionId(anyString()))
                .thenReturn(Optional.of(Transaction.builder()
                        .bookId(1L)
                        .memberId(1L)
                        .transactionId("TR001")
                        .quantity(1L)
                        .status(TransactionStatus.SUCCESS)
                        .durationInDay(1)
                        .transactionDate(new Date())
                        .build()));
        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(Member.builder()
                        .memberId("ME1000")
                        .name("Member")
                        .build()));
        when(bookRepository.findById(any()))
                .thenReturn(Optional.of(Book.builder()
                        .bookId("BO001")
                        .name("book")
                        .build()));
        TransactionDto response = transactionService.getTransaction("TR001");
        assertEquals("BO001", response.getBookId());
    }
}