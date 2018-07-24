package com.piolob.feecalculator.utils;

import com.piolob.feecalculator.exception.FeeException;
import com.piolob.feecalculator.service.FeeCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ShellProviderTest {
    private static final String ANY_STRING = "ANY_STRING";

    @Mock
    private FeeCalculator feeCalculator;
    @InjectMocks
    private ShellProvider shellProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnPositiveNumberWhenNoError() throws FeeException {
        when(feeCalculator.calculateFee(anyString(), anyString(), any(BigDecimal.class))).thenReturn(BigDecimal.ONE);

        BigDecimal result = shellProvider.calculatefee(ANY_STRING, ANY_STRING, BigDecimal.ONE);

        Assert.assertTrue(result.compareTo(BigDecimal.ONE)==0);
    }

    @Test
    public void shouldReturnNegativeNumberOnError() throws FeeException {
        when(feeCalculator.calculateFee(anyString(), anyString(), any(BigDecimal.class))).thenThrow(FeeException.class);

        BigDecimal result = shellProvider.calculatefee(ANY_STRING, ANY_STRING, BigDecimal.ONE);

        Assert.assertTrue(result.compareTo(BigDecimal.ONE) < 0);
    }
}
