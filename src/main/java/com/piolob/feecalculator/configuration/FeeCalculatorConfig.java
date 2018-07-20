package com.piolob.feecalculator.configuration;

import com.piolob.feecalculator.service.SearchCsvFileService;
import com.piolob.feecalculator.service.FeeCalculator;
import com.piolob.feecalculator.service.InmemoryFeeCalculator;
import com.piolob.feecalculator.service.StreamFeeCalculator;
import com.piolob.feecalculator.utils.ProcessingMode;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class FeeCalculatorConfig {

    public static final String CALCULATED_FEES = "calculatedFees";
    public static final String PROCESSED_CUSTOMER = "processedCustomer";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CALCULATED_FEES, PROCESSED_CUSTOMER);
    }

    @Bean
    FeeCalculator feeCalculator(SearchCsvFileService searchCsvFileService, GlobalProperties globalProperties) {
        switch (ProcessingMode.valueOf(globalProperties.getDefaultProcessingMode())) {
            case INMEMORY_MODE:
                return new InmemoryFeeCalculator(searchCsvFileService, globalProperties);
            case STREAM_MODE:
                return new StreamFeeCalculator(searchCsvFileService, globalProperties);
            default:
                return new InmemoryFeeCalculator(searchCsvFileService, globalProperties);
        }
    }
}