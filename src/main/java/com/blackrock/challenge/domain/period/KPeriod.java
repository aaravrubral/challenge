package com.blackrock.challenge.domain.period;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class KPeriod {
    private LocalDateTime start;
    private LocalDateTime end;
}
