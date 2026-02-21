package com.blackrock.challenge.domain.period;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class QPeriod {
    private LocalDateTime start;
    private LocalDateTime end;
    private double fixed;
}
