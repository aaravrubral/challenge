package com.blackrock.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PerformanceResponse {

    private String time;
    private String memory;
    private int threads;

    public PerformanceResponse(String time, String memory, int threads) {
        this.time = time;
        this.memory = memory;
        this.threads = threads;
    }

}
