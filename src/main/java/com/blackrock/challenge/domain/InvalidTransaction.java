package com.blackrock.challenge.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvalidTransaction extends Transaction {
    private String message;
}
