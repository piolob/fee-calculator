package com.piolob.feecalculator.configuration;

import com.piolob.feecalculator.exception.FeeException;
import com.piolob.feecalculator.model.CustomerFee;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.ConcurrentRowProcessor;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataFeeder {

    public static final String PROBLEM_WITH_INPUT_FILE = "Problem with input file";
    private Map<String, BigDecimal> feeDiscounts;
    private List<CustomerFee> customerFees;
    private GlobalProperties globalProperties;

    public DataFeeder(GlobalProperties globalProperties) {
        this.globalProperties = globalProperties;
    }

    @PostConstruct
    private void updateData() throws FeeException {
        updateCustomerFees();
        updateFeeDiscounts();
    }


    private Map<String, BigDecimal> feedFeeDiscounts() throws FeeException {
        CsvParserSettings settings = new CsvParserSettings();
        CsvParser parser = new CsvParser(settings);
        try {
            parser.beginParsing(globalProperties.getFeesDiscountsFileName());
        } catch (NullPointerException e) {
            throw new FeeException(PROBLEM_WITH_INPUT_FILE);
        }

        Map<String, BigDecimal> feeDiscounts = new HashMap<>();
        for (Record record : parser.iterateRecords(globalProperties.getFeesDiscountsFileName())) {
            if (record.getBigDecimal(1) != null) {
                feeDiscounts.putIfAbsent(record.getString(0), record.getBigDecimal(1));
            } else {
                feeDiscounts.putIfAbsent(record.getString(0), BigDecimal.ZERO);
            }
        }
        return feeDiscounts;
    }

    private List<CustomerFee> feedCustomerFees() throws FeeException {
        BeanListProcessor<CustomerFee> rowProcessor = new BeanListProcessor<>(CustomerFee.class);
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setProcessor(new ConcurrentRowProcessor(rowProcessor));
        CsvParser parser = new CsvParser(parserSettings);
        try {
            parser.beginParsing(globalProperties.getFeesDiscountsFileName());
        } catch (NullPointerException e) {
            throw new FeeException(PROBLEM_WITH_INPUT_FILE);
        }
        parser.parse(globalProperties.getCustomerFeesFileName());
        return rowProcessor.getBeans();
    }

    public void updateFeeDiscounts() throws FeeException {
        this.feeDiscounts = feedFeeDiscounts();
    }

    public void updateCustomerFees() throws FeeException {
        this.customerFees = feedCustomerFees();
    }

    public Map<String, BigDecimal> getFeeDiscounts() {
        return feeDiscounts;
    }

    public List<CustomerFee> getCustomerFees() {
        return customerFees;
    }
}
