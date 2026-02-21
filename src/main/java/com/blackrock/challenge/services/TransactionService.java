package com.blackrock.challenge.services;

import com.blackrock.challenge.domain.Expense;
import com.blackrock.challenge.domain.InvalidTransaction;
import com.blackrock.challenge.domain.Transaction;
import com.blackrock.challenge.dto.TemporalFilterRequest;
import com.blackrock.challenge.dto.TemporalFilterResponse;
import com.blackrock.challenge.dto.TransactionValidatorRequest;
import com.blackrock.challenge.dto.TransactionValidatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {


    @Autowired
    private TemporalRuleService temporalRuleService = null;

    public TransactionService() {
        this.temporalRuleService = temporalRuleService;
    }

    public TemporalFilterResponse applyTemporalRules(TemporalFilterRequest request) {
        return temporalRuleService.applyRules(
                request.getWage(),
                request.getTransactions(),
                request.getQ(),
                request.getP(),
                request.getK()
        );
    }
    public TransactionValidatorResponse validate(TransactionValidatorRequest request) {

        double wage = request.getWage();
        List<Transaction> transactions = request.getTransactions();

        List<Transaction> valid = new ArrayList<>(transactions.size());
        List<InvalidTransaction> invalid = new ArrayList<>();

        Set<LocalDateTime> seenDates = new HashSet<>(transactions.size());

        for (Transaction tx : transactions) {

            String error = null;

            if (tx.getDate() == null) {
                error = "Transaction date is null";
            } else if (!seenDates.add(tx.getDate())) {
                error = "Duplicate transaction timestamp";
            } else if (tx.getAmount() < 0) {
                error = "Transaction amount cannot be negative";
            } else if (tx.getCeiling() < tx.getAmount()) {
                error = "Ceiling must be greater than or equal to amount";
            } else if (tx.getRemanent() < 0) {
                error = "Remanent cannot be negative";
            } else if (tx.getRemanent() > wage) {
                error = "Remanent exceeds allowed wage limit";
            }

            if (error == null) {
                valid.add(tx);
            } else {
                InvalidTransaction it = new InvalidTransaction();
                it.setDate(tx.getDate());
                it.setAmount(tx.getAmount());
                it.setCeiling(tx.getCeiling());
                it.setRemanent(tx.getRemanent());
                it.setMessage(error);
                invalid.add(it);
            }
        }

        TransactionValidatorResponse response = new TransactionValidatorResponse();
        response.setValid(valid);
        response.setInvalid(invalid);

        return response;
    }

    public List<Transaction> buildTransactions(List<Expense> expenses) {

        if (expenses == null || expenses.isEmpty()) {
            return Collections.emptyList();
        }

        List<Transaction> result = new ArrayList<>(expenses.size());

        for (Expense e : expenses) {

            if (e == null) {
                continue; // defensive
            }

            double ceiling = Math.ceil(e.getAmount() / 100.0) * 100.0;
            double remanent = ceiling - e.getAmount();

            Transaction tx = new Transaction();
            tx.setDate(e.getDate());
            tx.setAmount(e.getAmount());
            tx.setCeiling(ceiling);
            tx.setRemanent(remanent);

            result.add(tx);
        }

        return result;
    }
}
