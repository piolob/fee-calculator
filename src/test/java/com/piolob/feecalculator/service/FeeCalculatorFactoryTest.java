package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.utils.ProcessingMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeeCalculatorFactoryTest {
    private static final String NOT_EXISTING_PROCESSING_MODE = "NOT EXISTING PROCESSING MODE";

    private FeeCalculatorFactory feeCalculatorFactory;
    @Mock
    private GlobalProperties globalProperties;
    @Mock
    private SearchCsvFileService searchCsvFileService;

    @Before
    public void setUp() {
        feeCalculatorFactory = new FeeCalculatorFactory(searchCsvFileService, globalProperties);
    }

    @Test
    public void shouldReturnInmemoryFeeCalculatorOnProperConfig(){
        when(globalProperties.getDefaultProcessingMode()).thenReturn(ProcessingMode.INMEMORY_MODE.name());

        FeeCalculator feeCalculator = feeCalculatorFactory.getFeeCalculator();

        Assert.assertTrue(feeCalculator instanceof InmemoryFeeCalculator);
    }

    @Test
    public void shouldReturnFileStreamFeeCalculatorOnProperConfig(){
        when(globalProperties.getDefaultProcessingMode()).thenReturn(ProcessingMode.STREAM_MODE.name());

        FeeCalculator feeCalculator = feeCalculatorFactory.getFeeCalculator();

        Assert.assertTrue(feeCalculator instanceof StreamFeeCalculator);
    }

    @Test
    public void shouldReturnFileStreamFeeCalculatorOnProperConfigByDefault(){
        when(globalProperties.getDefaultProcessingMode()).thenReturn(NOT_EXISTING_PROCESSING_MODE);

        FeeCalculator feeCalculator = feeCalculatorFactory.getFeeCalculator();

        Assert.assertTrue(feeCalculator instanceof StreamFeeCalculator);
    }
}