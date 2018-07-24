package com.piolob.feecalculator.configuration;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.io.File;

public class GlobalPropertiesValidator implements Validator {
    @Override
    public boolean supports(Class<?> type) {
        return type == GlobalProperties.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "defaultProcessingMode", "defaultProcessingMode.empty");
        ValidationUtils.rejectIfEmpty(errors, "inputDirectory", "inputDirectory.empty");
        ValidationUtils.rejectIfEmpty(errors, "customerFeesFile", "customerFeesFile.empty");
        ValidationUtils.rejectIfEmpty(errors, "feesDiscountsFile", "feesDiscountsFile.empty");
        GlobalProperties properties = (GlobalProperties) o;
        if (!validateDirectory(properties.getInputDirectory())) errors.rejectValue("inputDirectory", "", "inputDirectory directory not found");
        if (!validateFile(properties.getCustomerFeesFile())) errors.rejectValue("customerFeesFile", "","customerFeesFile not found" );
        if (!validateFile(properties.getFeesDiscountsFile())) errors.rejectValue("feesDiscountsFile", "","feesDiscountsFile not found");
    }

    private boolean validateFile(File file) {
        return file.exists() && !file.isDirectory();
    }

    private boolean validateDirectory(File file) {
        return file.exists() && file.isDirectory();
    }
}
