package io.bhimsur.librarian.controller;

import io.bhimsur.librarian.annotation.AdminPermit;
import io.bhimsur.librarian.dto.BaseResponse;
import io.bhimsur.librarian.dto.MemberDto;
import io.bhimsur.librarian.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    @AdminPermit
    public ResponseEntity<BaseResponse<MemberDto>> createMember(@RequestBody MemberDto request) {
        return new ResponseEntity<>(new BaseResponse<>(memberService.createMember(request)), HttpStatus.CREATED);
    }
}
