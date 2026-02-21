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
public class ReturnsRequest {

    private int age;
    private double wage;        // monthly salary
    private double inflation;   // annual inflation (e.g. 0.055)

    private List<QPeriod> q;
    private List<PPeriod> p;
    private List<KPeriod> k;

    private List<Transaction> transactions;

}
