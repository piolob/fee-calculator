package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.FeeCalculatorConfig;
import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.exception.FeeException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
abstract class AbstractFeeCalculator implements FeeCalculator {

    SearchCsvFileService searchCsvFileService;
    GlobalProperties globalProperties;

    public AbstractFeeCalculator(SearchCsvFileService searchCsvFileService, GlobalProperties globalProperties) {
        this.searchCsvFileService = searchCsvFileService;
        this.globalProperties = globalProperties;
    }

    @Cacheable(value = FeeCalculatorConfig.CALCULATED_FEES)
    public abstract BigDecimal calculateFee(String customerId, String currency, BigDecimal amount) throws FeeException;

}
