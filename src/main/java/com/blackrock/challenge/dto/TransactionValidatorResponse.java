package com.blackrock.challenge.dto;

import com.blackrock.challenge.domain.InvalidTransaction;
import com.blackrock.challenge.domain.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TransactionValidatorResponse {

    private List<Transaction> valid;
    private List<InvalidTransaction> invalid;

}
