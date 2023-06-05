package io.bhimsur.librarian.repository;

import io.bhimsur.librarian.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT * FROM member m WhERE m.id_card_number = ?1",
            nativeQuery = true)
    Optional<Member> findByIdCard(String idCard);
    Optional<Member> findByMemberId(String memberId);
}
