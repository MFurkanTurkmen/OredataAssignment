package com.oredata.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimit {
    private final Map<String, Integer> requestCount = new ConcurrentHashMap<>();

    @Value("${ratelimit}")
    private int rateLimit ;
    public boolean isRateLimitExceeded(String remoteAddr) {
        int count = requestCount.getOrDefault(remoteAddr, 0);
        if (count < rateLimit) {
            requestCount.put(remoteAddr, count + 1);
            return false;
        } else {
            return true;
        }
    }


}
