package com.piolob.feecalculator.configuration;

import com.piolob.feecalculator.utils.ProcessingMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class DataFeederIT {

    @Autowired
    GlobalProperties globalProperties;

    private DataFeeder dataFeeder;

    @Before
    public void setUp() {
        dataFeeder = new DataFeeder(globalProperties);
    }

    @Test
    public void shouldUpdateCustomerFeesOnInmemoryModeWhenUpdatingCustomerFees() {
        globalProperties.setDefaultProcessingMode(ProcessingMode.INMEMORY_MODE.name());

        dataFeeder.updateCustomerFees();

        Assert.assertNotNull(dataFeeder.getCustomerFees());
        Assert.assertTrue(dataFeeder.getCustomerFees().size()>0);
        Assert.assertNull(dataFeeder.getFeeDiscounts());
    }

    @Test
    public void shouldNotUpdateCustomerFeesOnOnStreamModeWhenUpdatingCustomerFees() {
        globalProperties.setDefaultProcessingMode(ProcessingMode.STREAM_MODE.name());

        dataFeeder.updateCustomerFees();

        Assert.assertNull(dataFeeder.getCustomerFees());
        Assert.assertNull(dataFeeder.getFeeDiscounts());
    }

    @Test
    public void shouldUpdateFeeDiscountsOnInmemoryModeWhenUpdatingFeeDiscounts() {
        globalProperties.setDefaultProcessingMode(ProcessingMode.INMEMORY_MODE.name());

        dataFeeder.updateFeeDiscounts();

        Assert.assertNotNull(dataFeeder.getFeeDiscounts());
        Assert.assertTrue(dataFeeder.getFeeDiscounts().size()>0);
        Assert.assertNull(dataFeeder.getCustomerFees());
    }


    @Test
    public void shouldUpdateFeeDiscountsOnStreamModeWhenUpdatingFeeDiscounts() {
        globalProperties.setDefaultProcessingMode(ProcessingMode.STREAM_MODE.name());

        dataFeeder.updateFeeDiscounts();

        Assert.assertNotNull(dataFeeder.getFeeDiscounts());
        Assert.assertTrue(dataFeeder.getFeeDiscounts().size()>0);
        Assert.assertNull(dataFeeder.getCustomerFees());
    }
}