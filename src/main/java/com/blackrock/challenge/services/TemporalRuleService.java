package com.blackrock.challenge.services;

import com.blackrock.challenge.domain.InvalidTransaction;
import com.blackrock.challenge.domain.Transaction;
import com.blackrock.challenge.domain.period.KPeriod;
import com.blackrock.challenge.domain.period.PPeriod;
import com.blackrock.challenge.domain.period.QPeriod;
import com.blackrock.challenge.dto.KResult;
import com.blackrock.challenge.dto.TemporalFilterResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TemporalRuleService {

    public TemporalFilterResponse applyRules(
            double wage,
            List<Transaction> transactions,
            List<QPeriod> qPeriods,
            List<PPeriod> pPeriods,
            List<KPeriod> kPeriods
    ) {

        List<Transaction> valid = new ArrayList<>();
        List<InvalidTransaction> invalid = new ArrayList<>();
        Set<LocalDateTime> seenDates = new HashSet<>();

        qPeriods.sort(Comparator.comparing(QPeriod::getStart));

        for (Transaction tx : transactions) {

            try {
                // 🔴 BASIC VALIDATION (this was missing)
                if (tx.getDate() == null) {
                    throw new IllegalArgumentException("Transaction date is null");
                }
                if (!seenDates.add(tx.getDate())) {
                    throw new IllegalArgumentException("Duplicate transaction timestamp");
                }
                if (tx.getAmount() < 0) {
                    throw new IllegalArgumentException("Transaction amount cannot be negative");
                }
                if (tx.getRemanent() < 0) {
                    throw new IllegalArgumentException("Remanent cannot be negative");
                }
                if (tx.getRemanent() > wage) {
                    throw new IllegalArgumentException("Remanent exceeds wage limit");
                }

                double remanent = tx.getRemanent();

                // 🟡 q rules (override)
                QPeriod selectedQ = null;
                for (QPeriod q : qPeriods) {
                    if (inRange(tx.getDate(), q.getStart(), q.getEnd())) {
                        if (selectedQ == null ||
                                q.getStart().isAfter(selectedQ.getStart())) {
                            selectedQ = q;
                        }
                    }
                }
                if (selectedQ != null) {
                    remanent = selectedQ.getFixed();
                }

                // 🟢 p rules (additive)
                for (PPeriod p : pPeriods) {
                    if (inRange(tx.getDate(), p.getStart(), p.getEnd())) {
                        remanent += p.getExtra();
                    }
                }

                if (remanent < 0) {
                    throw new IllegalArgumentException("Negative remanent after q/p rules");
                }

                tx.setRemanent(remanent);
                valid.add(tx);

            } catch (Exception ex) {
                InvalidTransaction it = new InvalidTransaction();
                it.setDate(tx.getDate());
                it.setAmount(tx.getAmount());
                it.setCeiling(tx.getCeiling());
                it.setRemanent(tx.getRemanent());
                it.setMessage(ex.getMessage());
                invalid.add(it);
            }
        }

        // 🔵 k grouping (only on valid transactions)
        List<KResult> kResults = new ArrayList<>();
        for (KPeriod k : kPeriods) {
            double sum = 0;
            for (Transaction tx : valid) {
                if (inRange(tx.getDate(), k.getStart(), k.getEnd())) {
                    sum += tx.getRemanent();
                }
            }
            kResults.add(new KResult(k.getStart(), k.getEnd(), sum));
        }

        TemporalFilterResponse response = new TemporalFilterResponse();
        response.setValid(valid);
        response.setInvalid(invalid);
        response.setSavingsByDates(kResults);

        return response;
    }

    private boolean inRange(LocalDateTime t, LocalDateTime s, LocalDateTime e) {
        return !t.isBefore(s) && !t.isAfter(e);
    }
}
