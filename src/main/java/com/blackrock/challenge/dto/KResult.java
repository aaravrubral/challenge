package com.blackrock.challenge.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class KResult {
    private LocalDateTime start;
    private LocalDateTime end;
    private double amount;

    public KResult(LocalDateTime start, LocalDateTime end, double amount) {
        this.start = start;
        this.end = end;
        this.amount = amount;
    }

}
