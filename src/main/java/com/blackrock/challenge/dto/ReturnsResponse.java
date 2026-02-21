package com.blackrock.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReturnsResponse {

    private double transactionsTotalAmount;
    private double transactionsTotalCeiling;
    private List<SavingsResult> savingsByDates;

}
