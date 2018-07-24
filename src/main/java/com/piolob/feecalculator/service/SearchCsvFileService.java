package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.DataFeeder;
import com.piolob.feecalculator.configuration.FeeCalculatorConfig;
import com.piolob.feecalculator.exception.FeeException;
import com.piolob.feecalculator.model.CustomerFee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SearchCsvFileService {

    public static final String NO_DATA_AVAILABLE_FOR_GIVEN_PARAMETERS = "No data available for given parameters";
    private DataFeeder dataFeeder;

    public SearchCsvFileService(DataFeeder dataFeeder) {
        this.dataFeeder = dataFeeder;
    }

    @Cacheable(value = FeeCalculatorConfig.PROCESSED_CUSTOMER)
    public BigDecimal findCustomerFee(String customerId, String currency) throws FeeException {
        Optional<CustomerFee> customerFeeOptional = dataFeeder.getCustomerFees().stream().filter(customerFee -> customerFee.getId().equals(customerId) && customerFee.getCurrency().equals(currency)).findFirst();
        return customerFeeOptional.orElseThrow(() -> new FeeException(NO_DATA_AVAILABLE_FOR_GIVEN_PARAMETERS)).getFee();
    }

    public BigDecimal findFeeDiscount(String currency) {
        return (dataFeeder.getFeeDiscounts().get(currency) != null) ? dataFeeder.getFeeDiscounts().get(currency) : BigDecimal.ZERO;
    }
}
