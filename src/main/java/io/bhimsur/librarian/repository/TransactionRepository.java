package io.bhimsur.librarian.repository;

import io.bhimsur.librarian.constant.TransactionState;
import io.bhimsur.librarian.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(String transactionId);
    List<Transaction> findByState(TransactionState state);

}
