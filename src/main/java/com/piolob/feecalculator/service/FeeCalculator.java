package com.piolob.feecalculator.service;

import com.piolob.feecalculator.exception.FeeException;

import java.math.BigDecimal;

/**
 * Interface used to count transaction fee for given client.
 */
public interface FeeCalculator {

    /**
     * Method that calculates transaction fee.
     * @param customerId id of given customer
     * @param currency currency
     * @param amount amount of money
     * @return Calculation result.
     * @throws FeeException custom exceptions that wraps problems with calculation
     */
    BigDecimal calculateFee(String customerId, String currency, BigDecimal amount) throws FeeException;

}
