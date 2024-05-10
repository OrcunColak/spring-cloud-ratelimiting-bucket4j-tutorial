package com.colak.springcloudratelimitingbucket4jtutorial.controller;

import com.colak.springcloudratelimitingbucket4jtutorial.service.RateLimitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limiting")

@RequiredArgsConstructor
public class RateLimitingController {

    private final RateLimitingService rateLimitingService;


    // http://localhost:8080/api/rate-limiting/resource
    @GetMapping("/resource")
    public String getResource() {
        // Retrieve API key from request headers or JWT token
        String apiKey = "test-api-key";

        if (rateLimitingService.allowRequest(apiKey)) {
            return "Resource api accessed successfully";
        } else {
            return "Rate limit exceeded. Please Try again later.";
        }
    }
}
