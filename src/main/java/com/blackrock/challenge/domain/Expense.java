package com.blackrock.challenge.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Expense {
    private LocalDateTime date;
    private double amount;
}
