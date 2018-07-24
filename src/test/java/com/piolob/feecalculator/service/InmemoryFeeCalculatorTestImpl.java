package com.piolob.feecalculator.service;

import com.piolob.feecalculator.utils.ProcessingMode;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InmemoryFeeCalculatorTestImpl<InmemoryFeeCalculator extends AbstractFeeCalculator> extends AbstractFeeCalculatorTest<InmemoryFeeCalculator> {
    public InmemoryFeeCalculatorTestImpl() {
    }

    @Override
    InmemoryFeeCalculator createInstance() {
        when(globalProperties.getDefaultProcessingMode()).thenReturn(ProcessingMode.INMEMORY_MODE.name());
        return (InmemoryFeeCalculator) feeCalculatorFactory.getFeeCalculator();
    }
}
