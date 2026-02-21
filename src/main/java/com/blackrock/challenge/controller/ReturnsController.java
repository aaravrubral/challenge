package com.blackrock.challenge.controller;

import com.blackrock.challenge.dto.ReturnsRequest;
import com.blackrock.challenge.dto.ReturnsResponse;
import com.blackrock.challenge.services.ReturnsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blackrock/challenge/v1/returns")
public class ReturnsController {

    private final ReturnsService returnsService;

    public ReturnsController(ReturnsService returnsService) {
        this.returnsService = returnsService;
    }

    @PostMapping("/nps")
    public ReturnsResponse nps(@RequestBody ReturnsRequest request) {
        if (request == null) throw new IllegalArgumentException("Request cannot be null");
        return returnsService.calculateNps(request);
    }

    @PostMapping("/index")
    public ReturnsResponse index(@RequestBody ReturnsRequest request) {
        if (request == null) throw new IllegalArgumentException("Request cannot be null");
        return returnsService.calculateIndex(request);
    }
}
