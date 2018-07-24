package com.piolob.feecalculator.configuration;

import com.piolob.feecalculator.model.CustomerFee;
import com.piolob.feecalculator.utils.ProcessingMode;
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

import static com.piolob.feecalculator.utils.ProcessingMode.INMEMORY_MODE;

@Component
public class DataFeeder {
    private Map<String, BigDecimal> feeDiscounts;
    private List<CustomerFee> customerFees;
    private GlobalProperties globalProperties;

    public DataFeeder(GlobalProperties globalProperties) {
        this.globalProperties = globalProperties;
    }

    @PostConstruct
    private void updateData() {
        updateCustomerFees();
        updateFeeDiscounts();
    }


    private Map<String, BigDecimal> feedFeeDiscounts() {
        CsvParserSettings settings = new CsvParserSettings();
        CsvParser parser = new CsvParser(settings);
        parser.beginParsing(globalProperties.getFeesDiscountsFile());

        Map<String, BigDecimal> feeDiscounts = new HashMap<>();
        for (Record record : parser.iterateRecords(globalProperties.getFeesDiscountsFile())) {
            if (record.getBigDecimal(1) != null) {
                feeDiscounts.putIfAbsent(record.getString(0), record.getBigDecimal(1));
            } else {
                feeDiscounts.putIfAbsent(record.getString(0), BigDecimal.ZERO);
            }
        }
        return feeDiscounts;
    }

    private List<CustomerFee> feedCustomerFees() {
        BeanListProcessor<CustomerFee> rowProcessor = new BeanListProcessor<>(CustomerFee.class);
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setProcessor(new ConcurrentRowProcessor(rowProcessor));
        CsvParser parser = new CsvParser(parserSettings);
        parser.beginParsing(globalProperties.getFeesDiscountsFile());
        parser.parse(globalProperties.getCustomerFeesFile());
        return rowProcessor.getBeans();
    }

    public void updateFeeDiscounts() {
        this.feeDiscounts = feedFeeDiscounts();
    }

    public void updateCustomerFees() {
        if (ProcessingMode.valueOf(globalProperties.getDefaultProcessingMode())==INMEMORY_MODE){
            this.customerFees = feedCustomerFees();
        }
    }

    public Map<String, BigDecimal> getFeeDiscounts() {
        return feeDiscounts;
    }

    public List<CustomerFee> getCustomerFees() {
        return customerFees;
    }
}
