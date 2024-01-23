package com.example.demo.service;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CacheEvictionService {

    private final CacheManager cacheManager;

    //Cache names
    private final List<String> cacheNames = Arrays.asList("nonManagerCDRates", "nonManagerCDHistoryRates", "managerCDHistoryRates", "managerCDRates");

    public CacheEvictionService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    // Use a loop to evict each cache separately
    @CacheEvict(cacheNames = {"nonManagerCDRates", "nonManagerCDHistoryRates", "managerCDHistoryRates", "managerCDRates"}, allEntries = true)
    @Scheduled(cron = "0 0 3 * * ?") // At 3 AM every day
    public void evictCachesAt3AM() {
        for (String cacheName : cacheNames) {
            cacheManager.getCache(cacheName).clear();
        }
    }
}
