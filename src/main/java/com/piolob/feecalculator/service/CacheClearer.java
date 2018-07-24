package com.piolob.feecalculator.service;

import org.springframework.cache.CacheManager;

public class CacheClearer {
    private final CacheManager cacheManager;

    CacheClearer(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void clearCache() {
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheManager.getCache(cacheName).clear();
        }
    }
}