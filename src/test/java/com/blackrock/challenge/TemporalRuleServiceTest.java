package com.blackrock.challenge;

// Test type: Unit
// Validation: q override + p addition + k grouping
// Command: mvn test

import com.blackrock.challenge.domain.Transaction;
import com.blackrock.challenge.domain.period.KPeriod;
import com.blackrock.challenge.domain.period.PPeriod;
import com.blackrock.challenge.domain.period.QPeriod;
import com.blackrock.challenge.dto.TemporalFilterResponse;
import com.blackrock.challenge.services.TemporalRuleService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TemporalRuleServiceTest {

    private final TemporalRuleService service = new TemporalRuleService();

    @Test
    void shouldApplyQAndPAndGroupByKCorrectly() {

        Transaction tx = new Transaction();
        tx.setDate(LocalDateTime.parse("2023-07-01T21:59:00"));
        tx.setAmount(620);
        tx.setCeiling(700);
        tx.setRemanent(80);

        QPeriod q = new QPeriod();
        q.setFixed(0);
        q.setStart(LocalDateTime.parse("2023-07-01T00:00:00"));
        q.setEnd(LocalDateTime.parse("2023-07-31T23:59:00"));

        PPeriod p = new PPeriod();
        p.setExtra(25);
        p.setStart(LocalDateTime.parse("2023-06-01T00:00:00"));
        p.setEnd(LocalDateTime.parse("2023-12-31T23:59:00"));

        KPeriod k = new KPeriod();
        k.setStart(LocalDateTime.parse("2023-01-01T00:00:00"));
        k.setEnd(LocalDateTime.parse("2023-12-31T23:59:00"));

        TemporalFilterResponse response =
                service.applyRules(
                        50000,
                        List.of(tx),
                        List.of(q),
                        List.of(p),
                        List.of(k)
                );

        assertEquals(1, response.getValid().size());
        assertEquals(0, response.getInvalid().size());

        // q overrides to 0, p adds 25
        assertEquals(25, response.getValid().get(0).getRemanent());

        assertEquals(25, response.getSavingsByDates().get(0).getAmount());
    }
}
