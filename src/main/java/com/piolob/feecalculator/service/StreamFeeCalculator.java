package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.FeeCalculatorConfig;
import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.exception.FeeException;
import com.piolob.feecalculator.utils.CustomerFeeParser;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;

public class StreamFeeCalculator extends AbstractFeeCalculator {

    StreamFeeCalculator(SearchCsvFileService searchCsvFileService, GlobalProperties globalProperties) {
        super(searchCsvFileService, globalProperties);
    }

    @Cacheable(value = FeeCalculatorConfig.CALCULATED_FEES)
    @Override
    public BigDecimal calculateFee(String customerId, String currency, BigDecimal amount) throws FeeException {
        final CustomerFeeParser customFeeProcessor = new CustomerFeeParser();
        customFeeProcessor.processCustomerFee(customerId, currency, globalProperties.getCustomerFeesFile());
        checkIfNeededDataIsAvailable(customFeeProcessor);
        BigDecimal discount = BigDecimal.ONE.subtract(searchCsvFileService.findFeeDiscount(currency)).multiply(customFeeProcessor.getResult().get());
        return amount.multiply(discount);
    }

    private void checkIfNeededDataIsAvailable(CustomerFeeParser customFeeProcessor) throws FeeException {
        if (!customFeeProcessor.getResult().isPresent()) {
            throw new FeeException("No data available for given parameters");
        }
    }

}
