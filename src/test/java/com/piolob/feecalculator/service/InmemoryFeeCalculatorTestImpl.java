package com.piolob.feecalculator.service;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InmemoryFeeCalculatorTestImpl<InmemoryFeeCalculator extends AbstractFeeCalculator> extends AbstractFeeCalculatorTest<InmemoryFeeCalculator> {
    @Override
    InmemoryFeeCalculator createInstance() {
//        when(globalProperties.getDefaultProcessingMode()).thenReturn(ProcessingMode.INMEMORY_MODE.name());
        return (InmemoryFeeCalculator) feeCalculatorFactory.getFeeCalculator();
    }
}
