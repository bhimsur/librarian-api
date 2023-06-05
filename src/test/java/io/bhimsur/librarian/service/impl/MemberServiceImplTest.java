package io.bhimsur.librarian.service.impl;

import io.bhimsur.librarian.dto.MemberDto;
import io.bhimsur.librarian.entity.Member;
import io.bhimsur.librarian.exception.GenericException;
import io.bhimsur.librarian.repository.MemberRepository;
import io.bhimsur.librarian.repository.SequenceRepository;
import io.bhimsur.librarian.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MemberServiceImpl.class)
public class MemberServiceImplTest {

    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private SequenceRepository sequenceRepository;

    @Autowired
    private MemberService memberService;

    @Test(expected = GenericException.class)
    public void createMemberAlreadyExists() {
        when(memberRepository.findByIdCard(any()))
                .thenReturn(Optional.of(new Member()));
        memberService.createMember(new MemberDto("abc", null, "123"));
    }

    @Test
    public void createMemberSuccess() {
        when(memberRepository.findByIdCard(any()))
                .thenReturn(Optional.empty());
        when(sequenceRepository.getSequence(any()))
                .thenReturn(new BigDecimal(1).toBigInteger());
        when(memberRepository.save(any()))
                .thenReturn(new Member(1L, "abc", "ME000000000001", "123"));
        MemberDto response = memberService.createMember(new MemberDto("abc", null, "123"));
        assertEquals("ME000000000001", response.getMemberId());
    }
}