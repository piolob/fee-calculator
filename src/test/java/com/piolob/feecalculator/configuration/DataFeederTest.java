package com.piolob.feecalculator.configuration;

import com.piolob.feecalculator.utils.ProcessingMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class DataFeederTest {
    private static final String ANY_STRING = "ANY_STRING";

    @Autowired
    GlobalProperties globalProperties;

    private DataFeeder dataFeeder;

    @Before
    public void setUp() {
        dataFeeder = new DataFeeder(globalProperties);
    }

    @Test
    public void shouldUpdateFeeDiscounts() {
        dataFeeder.updateFeeDiscounts();

        Assert.assertNotNull(dataFeeder.getFeeDiscounts());
        Assert.assertTrue(dataFeeder.getFeeDiscounts().size()>0);
    }

    @Test
    public void shouldUpdateCustomerFeesOnInmemoryMode() {
        globalProperties.setDefaultProcessingMode(ProcessingMode.INMEMORY_MODE.name());

        dataFeeder.updateCustomerFees();

        Assert.assertNotNull(dataFeeder.getCustomerFees());
        Assert.assertTrue(dataFeeder.getCustomerFees().size()>0);
    }

    @Test
    public void shouldNotUpdateCustomerFeesOnStreamMode() {
        globalProperties.setDefaultProcessingMode(ProcessingMode.STREAM_MODE.name());

        dataFeeder.updateCustomerFees();

        Assert.assertNull(dataFeeder.getCustomerFees());
    }

    @Test
    public void shouldUpdateAllOnInmemoryMode() {
        globalProperties.setDefaultProcessingMode(ProcessingMode.INMEMORY_MODE.name());

        dataFeeder.updateCustomerFees();

        Assert.assertNotNull(dataFeeder.getCustomerFees());
        Assert.assertTrue(dataFeeder.getCustomerFees().size()>0);
    }

    @Test
    public void shouldUpdateOnlyFeeDiscountsOnStreamMode() {
        globalProperties.setDefaultProcessingMode(ProcessingMode.INMEMORY_MODE.name());

        dataFeeder.updateCustomerFees();

        Assert.assertNotNull(dataFeeder.getCustomerFees());
        Assert.assertTrue(dataFeeder.getCustomerFees().size()>0);
        Assert.assertNull(dataFeeder.getCustomerFees());
    }
}