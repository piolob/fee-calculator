package com.piolob.feecalculator.utils;

import com.piolob.feecalculator.configuration.FeeCalculatorConfig;
import com.piolob.feecalculator.model.CustomerFee;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.BeanProcessor;
import com.univocity.parsers.common.processor.ConcurrentRowProcessor;
import com.univocity.parsers.common.processor.RowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.cache.annotation.Cacheable;

import java.io.File;
import java.math.BigDecimal;
import java.util.Optional;

public class CustomerFeeParser {
    private BigDecimal result;

    @Cacheable(value = FeeCalculatorConfig.PROCESSED_CUSTOMER)
    public void processCustomerFee(String customerId, String currency, File customerFeesFileName) {
        CsvParserSettings parserSettings = new CsvParserSettings();
        ConcurrentRowProcessor concurrentRowProcessor = new ConcurrentRowProcessor(createRowProcessor(customerId, currency));
        parserSettings.setProcessor(concurrentRowProcessor);
        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(customerFeesFileName);
    }

    private RowProcessor createRowProcessor(String customerId, String currency) {
        return new BeanProcessor<CustomerFee>(CustomerFee.class) {
            @Override
            public void beanProcessed(CustomerFee customerFee, ParsingContext parsingContext) {
                if (customerFee.getId().equals(customerId) && customerFee.getCurrency().equals(currency)) {
                    result = customerFee.getFee();
                    parsingContext.stop();
                }
            }
        };
    }

    public Optional<BigDecimal> getResult() {
        return Optional.ofNullable(result);
    }
}
