package com.blackrock.challenge.dto;

import com.blackrock.challenge.domain.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TransactionValidatorRequest {

    private double wage;
    private List<Transaction> transactions;

}
