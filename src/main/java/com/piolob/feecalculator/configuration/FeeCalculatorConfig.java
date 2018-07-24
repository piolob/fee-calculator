package com.piolob.feecalculator.configuration;

import com.piolob.feecalculator.service.*;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

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
    FeeCalculatorFactory feeCalculatorFactory(SearchCsvFileService searchCsvFileService, GlobalProperties globalProperties) {
        return new FeeCalculatorFactory(searchCsvFileService, globalProperties);
    }

    @Bean
    FeeCalculator feeCalculator(FeeCalculatorFactory feeCalculatorFactory) {
        return feeCalculatorFactory.getFeeCalculator();
    }

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new GlobalPropertiesValidator();
    }

}