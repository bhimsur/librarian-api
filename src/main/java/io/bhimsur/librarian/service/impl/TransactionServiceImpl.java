package io.bhimsur.librarian.service.impl;

import io.bhimsur.librarian.constant.Constant;
import io.bhimsur.librarian.constant.ErrorCode;
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
import io.bhimsur.librarian.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final TransactionRepository transactionRepository;
    private final SequenceRepository sequenceRepository;

    @Override
    public TransactionDto inquiry(TransactionDto request) {
        Optional<Book> bookOptional = bookRepository.findByBookId(request.getBookId());
        Book book = bookOptional.orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
        if (book.getStock() < 1 || book.getStock() < request.getQuantity()) {
            throw new GenericException(ErrorCode.BOOK_OUT_OF_STOCK);
        }
        Optional<Member> memberOptional = memberRepository.findByMemberId(request.getMemberId());
        Member member = memberOptional.orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));

        Transaction trx = transactionRepository.save(Transaction.builder()
                .transactionId(RandomUtil.getSequence(Constant.Prefix.TRANSACTION_PREFIX, sequenceRepository.getSequence(Constant.Sequence.TRANSACTION_SEQ)))
                .memberId(member.getId())
                .bookId(book.getId())
                .quantity(request.getQuantity())
                .status(TransactionStatus.SUCCESS)
                .state(TransactionState.INQUIRY)
                .durationInDay(request.getDuration())
                .transactionDate(new Date())
                .build());
        return TransactionDto.builder()
                .transactionId(trx.getTransactionId())
                .memberId(member.getMemberId())
                .build();
    }

    @Override
    public TransactionDto execute(TransactionDto request) {
        Optional<Transaction> trx = transactionRepository.findByTransactionId(request.getTransactionId());
        Transaction transaction = trx.orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
        if (TransactionState.EXECUTE.equals(transaction.getState())) {
            throw new GenericException(ErrorCode.ALREADY_EXECUTED);
        }

        Book book = bookRepository.findById(transaction.getBookId()).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));

        if (transaction.getQuantity() > book.getStock()) {
            throw new GenericException(ErrorCode.BOOK_OUT_OF_STOCK);
        }

        transaction.setState(TransactionState.EXECUTE);
        transaction.setModifiedDate(new Date());
        transaction.setModifiedReason("Execute Transaction");

        transaction = transactionRepository.save(transaction);

        book.setStock(book.getStock() - transaction.getQuantity());
        book.setModifiedDate(new Date());
        book.setModifiedReason("Update stock from transaction");
        book = bookRepository.save(book);

        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .bookName(book.getName())
                .status(transaction.getStatus().name())
                .build();
    }

    @Override
    public List<TransactionDto> getTransactions(String state) {
        List<Transaction> transactions;
        if (StringUtils.isEmpty(state)) {
            transactions = transactionRepository.findAll();
        } else {
            transactions = transactionRepository.findByState(TransactionState.valueOf(state));
        }
        if (transactions.isEmpty()) {
            return new ArrayList<>();
        }
        return transactions.stream()
                .map(trx -> {
                    Member member = memberRepository.findById(trx.getMemberId()).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
                    Book book = bookRepository.findById(trx.getBookId()).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
                    return TransactionDto.builder()
                            .transactionId(trx.getTransactionId())
                            .memberId(member.getMemberId())
                            .memberName(member.getName())
                            .bookId(book.getBookId())
                            .bookName(book.getName())
                            .quantity(trx.getQuantity())
                            .status(trx.getStatus().name())
                            .duration(trx.getDurationInDay())
                            .isLate(isLate(trx))
                            .transactionDate(trx.getTransactionDate())
                            .returnDate(trx.getReturnDate())
                            .state(trx.getState().name())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto returned(TransactionDto request) {
        Transaction trx = transactionRepository.findByTransactionId(request.getTransactionId()).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
        if (TransactionState.INQUIRY.equals(trx.getState())) {
            throw new GenericException(ErrorCode.INVALID_TRANSACTION);
        }
        Book book = bookRepository.findById(trx.getBookId()).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));

        trx.setReturnDate(StringUtils.isEmpty(request.getReturnDate()) ? new Date() : request.getReturnDate());
        trx.setModifiedDate(new Date());
        trx.setModifiedReason("Return Report");
        trx.setStatus(TransactionStatus.valueOf(request.getStatus()));

        trx = transactionRepository.save(trx);

        book.setStock(book.getStock() + trx.getQuantity());
        book.setModifiedDate(new Date());
        book.setModifiedReason("Update stock from transaction");
        book = bookRepository.save(book);

        return TransactionDto.builder()
                .transactionId(trx.getTransactionId())
                .bookName(book.getName())
                .status(trx.getStatus().name())
                .isLate(isLate(trx))
                .build();
    }

    @Override
    public TransactionDto getTransaction(String transactionId) {
        Transaction trx = transactionRepository.findByTransactionId(transactionId).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
        Member member = memberRepository.findById(trx.getMemberId()).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
        Book book = bookRepository.findById(trx.getBookId()).orElseThrow(() -> new GenericException(ErrorCode.DATA_NOT_FOUND));
        return TransactionDto.builder()
                .transactionId(trx.getTransactionId())
                .memberId(member.getMemberId())
                .memberName(member.getName())
                .bookId(book.getBookId())
                .bookName(book.getName())
                .quantity(trx.getQuantity())
                .status(trx.getStatus().name())
                .duration(trx.getDurationInDay())
                .isLate(isLate(trx))
                .transactionDate(trx.getTransactionDate())
                .returnDate(trx.getReturnDate())
                .build();
    }

    private boolean isLate(Transaction transaction) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(transaction.getTransactionDate());

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.DAY_OF_YEAR, cal1.get(Calendar.DAY_OF_YEAR) + transaction.getDurationInDay());
        Date expected = cal2.getTime();

        boolean late = new Date().after(expected);

        if (transaction.getReturnDate() == null && late) {
            return true;
        } else {
            return transaction.getReturnDate() != null && transaction.getReturnDate().after(new Date());
        }
    }
}
