package com.solvd.prendiodemo.validation;

import org.testng.asserts.SoftAssert;

public class SuccessMessageValidation {

    private final SoftAssert softAssert;

    public SuccessMessageValidation(SoftAssert softAssert) {
        this.softAssert = softAssert;
    }

    public void validateSuccessMessageVisible(boolean isVisible) {
        softAssert.assertTrue(isVisible, "Success message is not visible");
    }

    public void validateSuccessMessageText(String actual, String expected) {
        softAssert.assertEquals(actual, expected, "Success message is not visible");
    }
}
