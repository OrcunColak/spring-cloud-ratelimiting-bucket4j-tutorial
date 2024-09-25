package com.colak.springcloudratelimitingbucket4jtutorial.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitingService2 {

    private final Bucket bucket;

    public RateLimitingService2() {
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    // Block until token is available
    public void allowRequest(String apiKey) {
        bucket.asBlocking()
                .consumeUninterruptibly(1);
    }

    public boolean isRateLimited() {
        return bucket.tryConsume(1);
    }
}
