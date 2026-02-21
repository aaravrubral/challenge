package com.blackrock.challenge.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonPropertyOrder({ "date", "amount", "ceiling", "remanent" })
public class Transaction {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    private double amount;
    private double ceiling;
    private double remanent;
}
