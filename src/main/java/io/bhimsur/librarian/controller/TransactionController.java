package io.bhimsur.librarian.controller;

import io.bhimsur.librarian.annotation.AdminPermit;
import io.bhimsur.librarian.dto.BaseResponse;
import io.bhimsur.librarian.dto.TransactionDto;
import io.bhimsur.librarian.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings({"unchecked", "rawtypes"})
@RequestMapping("/transaction")
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @AdminPermit
    @PostMapping("/inquiry")
    public ResponseEntity<BaseResponse<TransactionDto>> inquiry(@RequestBody TransactionDto request) {
        return new ResponseEntity<>(new BaseResponse<>(transactionService.inquiry(request)), HttpStatus.CREATED);
    }

    @AdminPermit
    @PostMapping("/execute")
    public ResponseEntity<BaseResponse<TransactionDto>> execute(@RequestBody TransactionDto request) {
        return new ResponseEntity<>(new BaseResponse<>(transactionService.execute(request)), HttpStatus.CREATED);
    }

    @GetMapping
    @AdminPermit
    public <T extends Serializable> ResponseEntity<BaseResponse<T>> getTransaction(@RequestParam(name = "transaction_id", required = false) String transactionId,
                                                                                   @RequestParam(name = "state", required = false) String state) {
        if (StringUtils.isEmpty(transactionId)) {
            return new ResponseEntity(new BaseResponse<>(new ArrayList<>(transactionService.getTransactions(state))), HttpStatus.OK);
        } else {
            return new ResponseEntity(new BaseResponse<>(transactionService.getTransaction(transactionId)), HttpStatus.OK);
        }
    }

    @PutMapping("/returned")
    @AdminPermit
    public ResponseEntity<BaseResponse<TransactionDto>> returned(@RequestBody TransactionDto request) {
        return new ResponseEntity<>(new BaseResponse<>(transactionService.returned(request)), HttpStatus.OK);
    }

}
