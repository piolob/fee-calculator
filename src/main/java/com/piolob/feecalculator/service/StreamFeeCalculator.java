package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.FeeCalculatorConfig;
import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.exception.FeeException;
import com.piolob.feecalculator.utils.CustomerFeeParser;
import com.univocity.parsers.common.DataProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;

public class StreamFeeCalculator extends AbstractFeeCalculator {
    private static final Logger LOG = LoggerFactory.getLogger(StreamFeeCalculator.class);

    public StreamFeeCalculator(SearchCsvFileService searchCsvFileService, GlobalProperties globalProperties) {
        super(searchCsvFileService, globalProperties);
    }

    @Cacheable(value = FeeCalculatorConfig.CALCULATED_FEES)
    @Override
    public BigDecimal calculateFee(String customerId, String currency, BigDecimal amount) throws FeeException {
        LOG.info("no cache");
        CustomerFeeParser customFeeProcessor = new CustomerFeeParser();
        processCustomerFee(customerId, currency, customFeeProcessor);
        checkIfNeededDataIsAvailable(customFeeProcessor);
        BigDecimal discount = BigDecimal.ONE.subtract(searchCsvFileService.findFeeDiscount(currency)).multiply(customFeeProcessor.getResult().get());
        return amount.multiply(discount);
    }

    private void checkIfNeededDataIsAvailable(CustomerFeeParser customFeeProcessor) throws FeeException {
        if (!customFeeProcessor.getResult().isPresent()) {
            throw new FeeException("No data available for given parameters");
        }
    }

    private void processCustomerFee(String customerId, String currency, CustomerFeeParser customFeeProcessor) throws FeeException {
        try {
            customFeeProcessor.processCustomerFee(customerId, currency, globalProperties.getCustomerFeesFileName());
        } catch (DataProcessingException e) {
            throw new FeeException("Processing CSV error");
        }
    }

}
