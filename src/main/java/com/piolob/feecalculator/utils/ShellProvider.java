package com.piolob.feecalculator.utils;

import com.piolob.feecalculator.exception.FeeException;
import com.piolob.feecalculator.service.FeeCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@ShellComponent
public class ShellProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ShellProvider.class);

    private FeeCalculator feeCalculator;

    public ShellProvider(FeeCalculator feeCalculator) {
        this.feeCalculator = feeCalculator;
    }

    @ShellMethod("Calculates fee for given customerId, currency and amount")
    public BigDecimal calculatefee(String customerId, @Size(min = 3, max = 3) String currency, @Positive BigDecimal amount) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        try {
            bigDecimal = feeCalculator.calculateFee(customerId, currency, amount);
        } catch (FeeException e) {
            LOG.error(e.getMessage(), e);
        }
        return bigDecimal;//setScale(2, RoundingMode.HALF_UP)
    }
}