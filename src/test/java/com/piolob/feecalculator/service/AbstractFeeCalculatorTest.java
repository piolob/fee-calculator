package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.exception.FeeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public abstract class AbstractFeeCalculatorTest<T extends AbstractFeeCalculator> {
    private static final String MATCHING = "MATCHING";

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private
    SearchCsvFileService searchCsvFileService;
    @Mock
    GlobalProperties globalProperties;
    @Mock
    FeeCalculatorFactory feeCalculatorFactory;

    private T feeCalculator;

    @Before
    public void setUp() {
        feeCalculatorFactory = new FeeCalculatorFactory(searchCsvFileService, globalProperties);
        feeCalculator = createInstance();
    }

    abstract T createInstance();

    @Test
    public void shouldCalculateFeeForExistingFeeDiscountAndExistingCustomerFee() throws FeeException {
        BigDecimal findFeeDiscountResult = new BigDecimal("0.9");
        when(searchCsvFileService.findFeeDiscount(anyString())).thenReturn(findFeeDiscountResult);
        when(searchCsvFileService.findCustomerFee(anyString(), anyString())).thenReturn(BigDecimal.ONE);

        BigDecimal result = feeCalculator.calculateFee(MATCHING, MATCHING, BigDecimal.TEN);

        Assert.assertNotNull(result);
        //BigDecimal.ONE.subtract(searchCsvFileService.findFeeDiscount(currency)).multiply(searchCsvFileService.findCustomerFee(customerId, currency)).multiply(discount);
        //BigDecimal.ONE.subtract(BigDecimal("0.9")).multiply(BigDecimal.ONE).multiply(BigDecimal.TEN)
        Assert.assertEquals(result, BigDecimal.valueOf(1F));
    }

    @Test
    public void shouldCalculateFeeForNotExistingFeeDiscountAndExistingCustomerFee() throws FeeException {
        when(searchCsvFileService.findFeeDiscount(anyString())).thenReturn(BigDecimal.ZERO);
        when(searchCsvFileService.findCustomerFee(anyString(), anyString())).thenReturn(BigDecimal.ONE);

        BigDecimal result = feeCalculator.calculateFee(MATCHING, MATCHING, BigDecimal.TEN);

        Assert.assertNotNull(result);
        //BigDecimal.ONE.subtract(searchCsvFileService.findFeeDiscount(currency)).multiply(searchCsvFileService.findCustomerFee(customerId, currency)).multiply(discount);
        //BigDecimal.ONE.subtract(BigDecimal.ZERO).multiply(BigDecimal.ONE).multiply(BigDecimal.TEN)
        Assert.assertEquals(result, BigDecimal.TEN);
    }

    @Test
    public void shouldThrowExceptionForExistingFeeDiscountAndNotExistingCustomerFee() throws FeeException {
        when(searchCsvFileService.findFeeDiscount(anyString())).thenReturn(BigDecimal.ZERO);
        when(searchCsvFileService.findCustomerFee(anyString(), anyString())).thenThrow(new FeeException(SearchCsvFileService.NO_DATA_AVAILABLE_FOR_GIVEN_PARAMETERS));

        //then
        thrown.expect(FeeException.class);
        thrown.expectMessage(SearchCsvFileService.NO_DATA_AVAILABLE_FOR_GIVEN_PARAMETERS);

        //when
        feeCalculator.calculateFee(MATCHING, MATCHING, BigDecimal.TEN);
    }


}