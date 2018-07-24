package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.FeeCalculatorConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheClearerTest {
    @Mock
    private CacheManager cacheManager;

    @Mock
    private CacheClearer cacheClearer;
    @Mock
    private Cache cache;


    @Before
    public void setUp() {
        cacheClearer = new CacheClearer(cacheManager);
    }

    @Test
    public void shouldClearAllCache() {
        when(cacheManager.getCacheNames()).thenReturn(Collections.singletonList(FeeCalculatorConfig.CALCULATED_FEES));
        when(cacheManager.getCache(anyString())).thenReturn(cache);
        doNothing().when(cache).clear();

        cacheClearer.clearCache();

        Mockito.verify(cacheManager).getCacheNames();
        Mockito.verify(cache).clear();
    }

}