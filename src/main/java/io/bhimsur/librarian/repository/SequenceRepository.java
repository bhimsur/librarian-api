package io.bhimsur.librarian.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

@Repository
@Slf4j
public class SequenceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public BigInteger getSequence(String sequence) {
        String nativeQuery = String.format("select nextval('library_management_service.%s')", sequence);
        return (BigInteger) entityManager.createNativeQuery(nativeQuery).getSingleResult();
    }
}
