package com.blackrock.challenge;

// Test type: Unit
// Validation: Duplicate timestamp detection
// Command: mvn test

import com.blackrock.challenge.domain.Transaction;
import com.blackrock.challenge.dto.TransactionValidatorRequest;
import com.blackrock.challenge.dto.TransactionValidatorResponse;
import com.blackrock.challenge.services.TransactionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private final TransactionService service = new TransactionService();

    @Test
    void shouldDetectDuplicateTransactions() {

        Transaction tx1 = new Transaction();
        tx1.setDate(LocalDateTime.parse("2023-10-12T20:15:00"));
        tx1.setAmount(250);
        tx1.setCeiling(300);
        tx1.setRemanent(50);

        Transaction tx2 = new Transaction();
        tx2.setDate(LocalDateTime.parse("2023-10-12T20:15:00")); // duplicate
        tx2.setAmount(375);
        tx2.setCeiling(400);
        tx2.setRemanent(25);

        TransactionValidatorRequest request = new TransactionValidatorRequest();
        request.setWage(50000);
        request.setTransactions(List.of(tx1, tx2));

        TransactionValidatorResponse response = service.validate(request);

        assertEquals(1, response.getValid().size());
        assertEquals(1, response.getInvalid().size());
        assertTrue(response.getInvalid().get(0).getMessage().contains("Duplicate"));
    }
    // Test type: Unit
// Validation: Negative amount detection
// Command: mvn test

    @Test
    void shouldRejectNegativeAmount() {

        Transaction tx = new Transaction();
        tx.setDate(LocalDateTime.parse("2023-12-17T08:09:00"));
        tx.setAmount(-480);
        tx.setCeiling(500);
        tx.setRemanent(20);

        TransactionValidatorRequest request = new TransactionValidatorRequest();
        request.setWage(50000);
        request.setTransactions(List.of(tx));

        TransactionValidatorResponse response = service.validate(request);

        assertEquals(0, response.getValid().size());
        assertEquals(1, response.getInvalid().size());
        assertTrue(response.getInvalid().get(0).getMessage().contains("negative"));
    }
}
