package com.piolob.feecalculator.configuration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.io.File;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GlobalPropertiesValidatorTest {

    private static final String ANY_STRING = "ANY STRING";
    private GlobalPropertiesValidator globalPropertiesValidator;

    @Mock
    private File csvFile;
    @Mock
    private File directory;


    @Before
    public void setUp() {
        globalPropertiesValidator = new GlobalPropertiesValidator();

    }

    @Test
    public void shouldValidateOnProperData() {
        GlobalProperties globalProperties = new GlobalProperties(directory, csvFile, csvFile, ANY_STRING);
        when(csvFile.exists()).thenReturn(true);
        when(directory.exists()).thenReturn(true);
        when(csvFile.isDirectory()).thenReturn(false);
        when(directory.isDirectory()).thenReturn(true);
        final Errors errors = new BeanPropertyBindingResult(globalProperties, "globalProperties");

        globalPropertiesValidator.validate(globalProperties, errors);

        Assert.assertFalse(errors.hasFieldErrors());
    }

    @Test
    public void shouldNotValidateWhenWrongDirectoryFile() {
        GlobalProperties globalProperties = new GlobalProperties(csvFile, csvFile, csvFile, ANY_STRING);
        when(csvFile.exists()).thenReturn(true);
        when(csvFile.isDirectory()).thenReturn(false);
        final Errors errors = new BeanPropertyBindingResult(globalProperties, "globalProperties");

        globalPropertiesValidator.validate(globalProperties, errors);

        Assert.assertTrue(errors.hasFieldErrors("inputDirectory"));
        Assert.assertEquals(errors.getFieldErrorCount(), 1);
    }

    @Test
    public void shouldNotValidateWhenRequiredPropertyIsMissing() {
        GlobalProperties globalProperties = new GlobalProperties(directory, csvFile, csvFile, null);
        when(csvFile.exists()).thenReturn(true);
        when(directory.exists()).thenReturn(true);
        when(csvFile.isDirectory()).thenReturn(false);
        when(directory.isDirectory()).thenReturn(true);
        final Errors errors = new BeanPropertyBindingResult(globalProperties, "globalProperties");

        globalPropertiesValidator.validate(globalProperties, errors);

        Assert.assertTrue(errors.hasFieldErrors("defaultProcessingMode"));
    }
}