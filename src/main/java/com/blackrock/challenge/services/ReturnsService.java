package com.blackrock.challenge.services;

import com.blackrock.challenge.domain.Transaction;
import com.blackrock.challenge.domain.period.KPeriod;
import com.blackrock.challenge.dto.ReturnsRequest;
import com.blackrock.challenge.dto.ReturnsResponse;
import com.blackrock.challenge.dto.SavingsResult;
import com.blackrock.challenge.util.TaxCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnsService {

        private static final double NPS_RATE = 0.0711;
        private static final double INDEX_RATE = 0.1449;

        public ReturnsResponse calculateNps(ReturnsRequest request) {
            return calculate(request, NPS_RATE, true);
        }

        public ReturnsResponse calculateIndex(ReturnsRequest request) {
            return calculate(request, INDEX_RATE, false);
        }

        private ReturnsResponse calculate(
                ReturnsRequest request,
                double rate,
                boolean isNps
        ) {

            int years = request.getAge() < 60 ? 60 - request.getAge() : 5;
            double annualIncome = request.getWage() * 12;

            double totalAmount = 0;
            double totalCeiling = 0;

            for (Transaction tx : request.getTransactions()) {
                totalAmount += tx.getAmount();
                totalCeiling += tx.getCeiling();
            }

            List<SavingsResult> results = new ArrayList<>(request.getK().size());

            for (KPeriod k : request.getK()) {

                double invested = 0;
                for (Transaction tx : request.getTransactions()) {
                    if (inRange(tx.getDate(), k.getStart(), k.getEnd())) {
                        invested += tx.getRemanent();
                    }
                }

                // Compound interest
                double futureValue =
                        invested * Math.pow(1 + rate, years);

                // Inflation adjustment
                double realValue =
                        futureValue / Math.pow(1 + request.getInflation(), years);

                double profit = realValue - invested;
                double taxBenefit = 0;

                if (isNps) {
                    taxBenefit = calculateNpsTaxBenefit(
                            invested,
                            annualIncome
                    );
                }

                results.add(new SavingsResult(
                        k.getStart(),
                        k.getEnd(),
                        invested,
                        round(profit),
                        round(taxBenefit)
                ));
            }

            ReturnsResponse response = new ReturnsResponse();
            response.setTransactionsTotalAmount(round(totalAmount));
            response.setTransactionsTotalCeiling(round(totalCeiling));
            response.setSavingsByDates(results);

            return response;
        }

        private double calculateNpsTaxBenefit(double invested, double annualIncome) {

            double deduction = Math.min(
                    invested,
                    Math.min(annualIncome * 0.10, 200000)
            );

            double taxBefore = TaxCalculator.tax(annualIncome);
            double taxAfter = TaxCalculator.tax(annualIncome - deduction);

            return taxBefore - taxAfter;
        }

        private boolean inRange(LocalDateTime t, LocalDateTime s, LocalDateTime e) {
            return !t.isBefore(s) && !t.isAfter(e);
        }

        private double round(double v) {
            return Math.round(v * 100.0) / 100.0;
        }

}
