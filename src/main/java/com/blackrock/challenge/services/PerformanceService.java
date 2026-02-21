package com.blackrock.challenge.services;

import com.blackrock.challenge.dto.PerformanceResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class PerformanceService {

    public PerformanceResponse getMetrics(long startTimeMillis) {

        long endTimeMillis = System.currentTimeMillis();
        long duration = endTimeMillis - startTimeMillis;

        Runtime runtime = Runtime.getRuntime();

        double usedMemoryMb =
                (runtime.totalMemory() - runtime.freeMemory()) / (1024.0 * 1024.0);

        int threadCount = Thread.activeCount();

        return new PerformanceResponse(
                duration + " ms",
                String.format("%.2f MB", usedMemoryMb),
                threadCount
        );
    }
}
