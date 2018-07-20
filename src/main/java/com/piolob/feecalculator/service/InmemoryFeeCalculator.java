package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.exception.FeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class InmemoryFeeCalculator extends AbstractFeeCalculator {
    private static final Logger LOG = LoggerFactory.getLogger(InmemoryFeeCalculator.class);

    public InmemoryFeeCalculator(SearchCsvFileService searchCsvFileService, GlobalProperties globalProperties) {
        super(searchCsvFileService, globalProperties);
    }

    @Override
    public BigDecimal calculateFee(String customerId, String currency, BigDecimal amount) throws FeeException {
        LOG.info("Calculating fee - no cache used");
        BigDecimal discount = BigDecimal.ONE.subtract(searchCsvFileService.findFeeDiscount(currency)).multiply(searchCsvFileService.findCustomerFee(customerId, currency));
        return amount.multiply(discount);
    }

}
