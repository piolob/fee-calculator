package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.DataFeeder;
import com.piolob.feecalculator.exception.FeeException;
import com.piolob.feecalculator.model.CustomerFee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchCsvFileServiceTest {
    private static final String MATCHING = "MATCHING";
    private static final String NOT_MATCHING = "NOT MATCHING";
    @Mock
    private DataFeeder dataFeeder;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SearchCsvFileService searchCsvFileService;

    @Before
    public void setUp() {
        searchCsvFileService = new SearchCsvFileService(dataFeeder);
    }

    @Test
    public void shouldFindCustomerFeeForExistingCustomer() throws FeeException {
        when(dataFeeder.getCustomerFees()).thenReturn(createFeedCustomerFees(MATCHING, BigDecimal.ONE));

        BigDecimal result = searchCsvFileService.findCustomerFee(MATCHING, MATCHING);

        Assert.assertEquals(result, BigDecimal.ONE);
    }

    @Test
    public void shouldThrowExceptionForNOTExistingCustomer() throws FeeException {
        when(dataFeeder.getCustomerFees()).thenReturn(createFeedCustomerFees(NOT_MATCHING, BigDecimal.ONE));

        //then
        thrown.expect(FeeException.class);
        thrown.expectMessage(SearchCsvFileService.NO_DATA_AVAILABLE_FOR_GIVEN_PARAMETERS);

        //when
        searchCsvFileService.findCustomerFee(MATCHING, MATCHING);
    }

    @Test
    public void shouldFindFeeDiscountForExistingFeeDiscount() {
        when(dataFeeder.getFeeDiscounts()).thenReturn(createFeeDiscounts(MATCHING, BigDecimal.ONE));

        BigDecimal result = searchCsvFileService.findFeeDiscount(MATCHING);

        Assert.assertEquals(result, BigDecimal.ONE);
    }

    @Test
    public void shouldReturnZeroDiscountForNotExistingFeeDiscount() {
        when(dataFeeder.getFeeDiscounts()).thenReturn(createFeeDiscounts(MATCHING, BigDecimal.ONE));

        BigDecimal result = searchCsvFileService.findFeeDiscount(NOT_MATCHING);

        Assert.assertNotEquals(result, BigDecimal.ONE);
    }

    private Map<String, BigDecimal> createFeeDiscounts(String currency, BigDecimal amount) {
        return Collections.singletonMap(currency, amount);
    }

    public List<CustomerFee> createFeedCustomerFees(String stringParams, BigDecimal fee) {
        return Collections.singletonList(new CustomerFee(stringParams, stringParams, fee));
    }

}