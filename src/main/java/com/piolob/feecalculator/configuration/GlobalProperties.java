package com.piolob.feecalculator.configuration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="fee")
public class GlobalProperties {

    @NotNull
    private File inputDirectoryName;
    @Positive
    @NotNull
    private int maxInputSizeInKB;
    @NotNull
    private File customerFeesFileName;
    @NotNull
    private File feesDiscountsFileName;
    @NotNull
    private String defaultProcessingMode;

    public File getInputDirectoryName() {
        return inputDirectoryName;
    }

    public void setInputDirectoryName(File inputDirectoryName) {
        this.inputDirectoryName = inputDirectoryName;
    }

    public int getMaxInputSizeInKB() {
        return maxInputSizeInKB;
    }

    public void setMaxInputSizeInKB(int maxInputSizeInKB) {
        this.maxInputSizeInKB = maxInputSizeInKB;
    }

    public File getCustomerFeesFileName() {
        return customerFeesFileName;
    }

    public void setCustomerFeesFileName(File customerFeesFileName) {
        this.customerFeesFileName = customerFeesFileName;
    }

    public File getFeesDiscountsFileName() {
        return feesDiscountsFileName;
    }

    public void setFeesDiscountsFileName(File feesDiscountsFileName) {
        this.feesDiscountsFileName = feesDiscountsFileName;
    }

    public String getDefaultProcessingMode() {
        return defaultProcessingMode;
    }

    public void setDefaultProcessingMode(String defaultProcessingMode) {
        this.defaultProcessingMode = defaultProcessingMode;
    }
}
