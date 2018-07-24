package com.piolob.feecalculator.service;

import com.piolob.feecalculator.utils.ProcessingMode;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StreamFeeCalculatorTestImpl<StreamFeeCalculator extends AbstractFeeCalculator> extends AbstractFeeCalculatorTest<StreamFeeCalculator> {
    @Override
    StreamFeeCalculator createInstance() {
        when(globalProperties.getDefaultProcessingMode()).thenReturn(ProcessingMode.INMEMORY_MODE.name());
        return (StreamFeeCalculator) feeCalculatorFactory.getFeeCalculator();
    }
}
