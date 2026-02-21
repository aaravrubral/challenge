package com.blackrock.challenge.controller;

import com.blackrock.challenge.domain.Expense;
import com.blackrock.challenge.domain.Transaction;
import com.blackrock.challenge.dto.TemporalFilterRequest;
import com.blackrock.challenge.dto.TemporalFilterResponse;
import com.blackrock.challenge.dto.TransactionValidatorRequest;
import com.blackrock.challenge.dto.TransactionValidatorResponse;
import com.blackrock.challenge.services.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blackrock/challenge/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/parse")
    public List<Transaction> parse(@RequestBody List<Expense> expenses) {
        return transactionService.buildTransactions(expenses);
    }

    @PostMapping("/validator")
    public TransactionValidatorResponse validate(
            @RequestBody TransactionValidatorRequest request
    ) {
        if (request == null) throw new IllegalArgumentException("Request cannot be null");
        return transactionService.validate(request);
    }

    @PostMapping("/filter")
    public TemporalFilterResponse filter(@RequestBody TemporalFilterRequest request) {
        if (request == null) throw new IllegalArgumentException("Request cannot be null");
        return transactionService.applyTemporalRules(request);
    }
}
