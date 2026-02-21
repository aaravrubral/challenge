package com.blackrock.challenge.dto;

import com.blackrock.challenge.domain.InvalidTransaction;
import com.blackrock.challenge.domain.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TemporalFilterResponse {

    private List<Transaction> valid;
    private List<InvalidTransaction> invalid;
    private List<KResult> savingsByDates;

}
