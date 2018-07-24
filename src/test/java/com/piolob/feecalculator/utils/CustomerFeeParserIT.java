package com.piolob.feecalculator.utils;

import com.piolob.feecalculator.configuration.GlobalProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class CustomerFeeParserIT {
    private static final String SAMPLE_USER_ID = "123321123";
    private static final String SAMPLE_CURRENCY = "PLN";
    private CustomerFeeParser customerFeeParser;

    @Autowired
    GlobalProperties globalProperties;

    @Before
    public void setup() {
        customerFeeParser = new CustomerFeeParser();
    }

    @Test
    public void processedUserIsRetrievedFromCsvOnRealData() {
        customerFeeParser.processCustomerFee(SAMPLE_USER_ID, SAMPLE_CURRENCY, globalProperties.getCustomerFeesFile());

        Assert.assertTrue(customerFeeParser.getResult().isPresent());
        Assert.assertTrue(customerFeeParser.getResult().get().compareTo(BigDecimal.ZERO)==1);
    }

    @Test
    public void processedUserIsNotRetrievedFromCsvWhenNotExistingCustomerId() {
        String notExistingUserId = "123";
        customerFeeParser.processCustomerFee(notExistingUserId, SAMPLE_CURRENCY, globalProperties.getCustomerFeesFile());

        Assert.assertFalse(customerFeeParser.getResult().isPresent());
    }

    @Test
    public void processedUserIsNotRetrievedFromCsvWhenWrongCurrency() {
        String notExistingCurrency = "NO_CURRENCY";
        customerFeeParser.processCustomerFee(SAMPLE_USER_ID, notExistingCurrency, globalProperties.getFeesDiscountsFile());

        Assert.assertFalse(customerFeeParser.getResult().isPresent());
    }
}