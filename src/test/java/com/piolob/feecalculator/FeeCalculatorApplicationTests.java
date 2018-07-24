package com.piolob.feecalculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class FeeCalculatorApplicationTests {

    private static final String PROPER_TEST_DATA = "calculatefee 123321123 PLN 150";
    private static final String WRONG_VALUE_TYPE = "should_be_BigDecimal";
    private static final String NOT_PROPER_TEST_DATA = "calculatefee 123321123 PLN" + WRONG_VALUE_TYPE;

    @Autowired
    private Shell shell;

    @Test
    public void commandCheck() {
        Object commandCheck = shell.evaluate(() -> "help");
        assertNotNull(commandCheck);
    }

    @Test
    public void calculateFeeExecutionReturnPositiveResultOnProperData() {
        Object commandCheck = shell.evaluate(() ->
                PROPER_TEST_DATA);

        assertNotNull(commandCheck);
        assertTrue(commandCheck instanceof BigDecimal);
        assertTrue(((BigDecimal) commandCheck).signum()> 0);
    }

    @Test
    public void calculateFeeExecutionCatchVerificationErrorOnNotProperData() {
        Object commandCheck = shell.evaluate(() ->
                NOT_PROPER_TEST_DATA);

        assertNotNull(commandCheck);
        assertTrue(commandCheck instanceof Exception);
    }
}
