package com.piolob.feecalculator.utils;

import org.springframework.cache.CacheManager;

public class CacheClearer {
    private final CacheManager cacheManager;

    public CacheClearer(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void clearCache() {
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheManager.getCache(cacheName).clear();
        }
    }
}