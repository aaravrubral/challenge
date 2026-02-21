package com.blackrock.challenge.domain.period;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PPeriod {
    private LocalDateTime start;
    private LocalDateTime end;
    private double extra;
}
