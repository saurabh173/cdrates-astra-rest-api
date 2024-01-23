package com.example.demo.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private final int maxRequests;
    private final long intervalMillis;
    private final Map<String, TokenBucket> tokenBuckets;

    public RateLimiter(int maxRequests, int intervalMinutes) {
        this.maxRequests = maxRequests;
        this.intervalMillis = intervalMinutes * 60 * 1000;
        this.tokenBuckets = new ConcurrentHashMap<>();
    }

    public boolean tryAcquire(String sourceIp) {
        TokenBucket tokenBucket = tokenBuckets.computeIfAbsent(sourceIp, k -> new TokenBucket());
        return tokenBucket.tryAcquire();
    }

    private class TokenBucket {
        private long lastRequestTime;
        private int tokens;

        private TokenBucket() {
            this.lastRequestTime = System.currentTimeMillis();
            this.tokens = maxRequests;
        }

        public synchronized boolean tryAcquire() {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastRequestTime;

            // Refill the bucket based on elapsed time
            tokens += (int) (elapsedTime / intervalMillis);
            tokens = Math.min(tokens, maxRequests);

            // Try to acquire a token
            if (tokens > 0) {
                tokens--;
                lastRequestTime = currentTime;
                return true; // Token acquired
            } else {
                return false; // Rate limit exceeded
            }
        }
    }
}

