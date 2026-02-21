package com.blackrock.challenge.dto;

import com.blackrock.challenge.domain.Transaction;
import com.blackrock.challenge.domain.period.KPeriod;
import com.blackrock.challenge.domain.period.PPeriod;
import com.blackrock.challenge.domain.period.QPeriod;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TemporalFilterRequest {

    private double wage;
    private List<QPeriod> q;
    private List<PPeriod> p;
    private List<KPeriod> k;
    private List<Transaction> transactions;

}
