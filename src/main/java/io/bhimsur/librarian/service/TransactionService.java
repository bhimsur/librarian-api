package io.bhimsur.librarian.service;

import io.bhimsur.librarian.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    TransactionDto inquiry(TransactionDto request);
    TransactionDto execute(TransactionDto request);
    List<TransactionDto> getTransactions(String state);
    TransactionDto returned(TransactionDto request);
    TransactionDto getTransaction(String transactionId);
}
