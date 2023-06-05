package io.bhimsur.librarian.service.impl;

import io.bhimsur.librarian.constant.Constant;
import io.bhimsur.librarian.constant.ErrorCode;
import io.bhimsur.librarian.dto.MemberDto;
import io.bhimsur.librarian.entity.Member;
import io.bhimsur.librarian.exception.GenericException;
import io.bhimsur.librarian.repository.MemberRepository;
import io.bhimsur.librarian.repository.SequenceRepository;
import io.bhimsur.librarian.service.MemberService;
import io.bhimsur.librarian.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final SequenceRepository sequenceRepository;

    @Override
    public MemberDto createMember(MemberDto request) {
        Optional<Member> byIdCard = memberRepository.findByIdCard(request.getIdCardNumber());
        if (byIdCard.isPresent()) {
            throw new GenericException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        Member member = memberRepository.save(Member.builder()
                .memberId(RandomUtil.getSequence(Constant.Prefix.MEMBER_PREFIX, sequenceRepository.getSequence(Constant.Sequence.MEMBER_SEQ)))
                .name(request.getName())
                .idCardNumber(request.getIdCardNumber())
                .build());

        return MemberDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .build();
    }
}
