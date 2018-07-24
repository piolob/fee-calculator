package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.exception.FeeException;

import java.math.BigDecimal;

public class InmemoryFeeCalculator extends AbstractFeeCalculator {

    InmemoryFeeCalculator(SearchCsvFileService searchCsvFileService, GlobalProperties globalProperties) {
        super(searchCsvFileService, globalProperties);
    }

    @Override
    public BigDecimal calculateFee(String customerId, String currency, BigDecimal amount) throws FeeException {
            BigDecimal discount = BigDecimal.ONE.subtract(searchCsvFileService.findFeeDiscount(currency)).multiply(searchCsvFileService.findCustomerFee(customerId, currency));
        return amount.multiply(discount);
    }

}
