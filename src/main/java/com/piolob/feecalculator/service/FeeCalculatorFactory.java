package com.piolob.feecalculator.service;

import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.utils.ProcessingMode;
import org.apache.commons.lang3.EnumUtils;

import static com.piolob.feecalculator.utils.ProcessingMode.INMEMORY_MODE;
import static com.piolob.feecalculator.utils.ProcessingMode.STREAM_MODE;

public class FeeCalculatorFactory {
    private SearchCsvFileService searchCsvFileService;
    private GlobalProperties globalProperties;

    public FeeCalculatorFactory(SearchCsvFileService searchCsvFileService, GlobalProperties globalProperties) {
        this.searchCsvFileService = searchCsvFileService;
        this.globalProperties = globalProperties;
    }

    public FeeCalculator getFeeCalculator() {
        ProcessingMode processingMode = EnumUtils.getEnum(ProcessingMode.class, globalProperties.getDefaultProcessingMode());
        if (processingMode == null) {
            return new StreamFeeCalculator(searchCsvFileService, globalProperties);
        }
        switch (processingMode) {
            case INMEMORY_MODE:
                return new InmemoryFeeCalculator(searchCsvFileService, globalProperties);
            case STREAM_MODE:
                return new StreamFeeCalculator(searchCsvFileService, globalProperties);
            default:
                return new StreamFeeCalculator(searchCsvFileService, globalProperties);
        }
    }
}

