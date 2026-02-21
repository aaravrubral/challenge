package com.blackrock.challenge.controller;

import com.blackrock.challenge.dto.PerformanceResponse;
import com.blackrock.challenge.services.PerformanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blackrock/challenge/v1/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping
    public PerformanceResponse metrics() {

        long startTime = System.currentTimeMillis();

        return performanceService.getMetrics(startTime);
    }
}
