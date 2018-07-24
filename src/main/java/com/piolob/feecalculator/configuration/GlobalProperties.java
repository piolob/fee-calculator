package com.piolob.feecalculator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.File;

@EnableConfigurationProperties
@ConfigurationProperties(prefix="fee")
@Validated
@Component
public class GlobalProperties {

    private File inputDirectory;
    private File customerFeesFile;
    private File feesDiscountsFile;
    private String defaultProcessingMode;

    public GlobalProperties() {
    }

    public GlobalProperties(File inputDirectory, File customerFeesFile, File feesDiscountsFile, String defaultProcessingMode) {
        this.inputDirectory = inputDirectory;
        this.customerFeesFile = customerFeesFile;
        this.feesDiscountsFile = feesDiscountsFile;
        this.defaultProcessingMode = defaultProcessingMode;
    }

    public File getInputDirectory() {
        return inputDirectory;
    }

    public void setInputDirectory(File inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    public File getCustomerFeesFile() {
        return customerFeesFile;
    }

    public void setCustomerFeesFile(File customerFeesFile) {
        this.customerFeesFile = customerFeesFile;
    }

    public File getFeesDiscountsFile() {
        return feesDiscountsFile;
    }

    public void setFeesDiscountsFile(File feesDiscountsFile) {
        this.feesDiscountsFile = feesDiscountsFile;
    }

    public String getDefaultProcessingMode() {
        return defaultProcessingMode;
    }

    public void setDefaultProcessingMode(String defaultProcessingMode) {
        this.defaultProcessingMode = defaultProcessingMode;
    }
}
