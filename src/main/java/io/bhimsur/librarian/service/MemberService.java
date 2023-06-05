package io.bhimsur.librarian.service;

import io.bhimsur.librarian.dto.MemberDto;

public interface MemberService {
    MemberDto createMember(MemberDto request);
}
