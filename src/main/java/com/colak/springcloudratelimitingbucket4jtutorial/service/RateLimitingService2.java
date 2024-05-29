package com.colak.springcloudratelimitingbucket4jtutorial.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService2 {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public void allowRequest(String apiKey) {
        Bucket bucket = buckets.computeIfAbsent(apiKey, this::createNewBucket);
        bucket.asBlocking()
                .consumeUninterruptibly(1);
    }

    private Bucket createNewBucket(String apiKey) {
        Bandwidth limit = Bandwidth.simple(10, Duration.ofSeconds(1));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
