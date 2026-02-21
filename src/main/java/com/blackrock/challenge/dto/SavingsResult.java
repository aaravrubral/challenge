package com.blackrock.challenge.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SavingsResult {

    private LocalDateTime start;
    private LocalDateTime end;
    private double amount;
    private double profit;
    private double taxBenefit;

    public SavingsResult(LocalDateTime start, LocalDateTime end,
                         double amount, double profit, double taxBenefit) {
        this.start = start;
        this.end = end;
        this.amount = amount;
        this.profit = profit;
        this.taxBenefit = taxBenefit;
    }

}
