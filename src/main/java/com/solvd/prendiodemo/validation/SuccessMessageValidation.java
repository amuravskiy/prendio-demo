package com.solvd.prendiodemo.validation;

import com.solvd.prendiodemo.web.pages.BasePage;
import org.testng.asserts.SoftAssert;

public class SuccessMessageValidation {

    private final SoftAssert softAssert;
    private final BasePage page;

    public SuccessMessageValidation(SoftAssert softAssert, BasePage page) {
        this.softAssert = softAssert;
        this.page = page;
    }

    public void validateSuccessMessageVisibleWithText(String expected) {
        softAssert.assertTrue(page.isSuccessMessageVisible(), "Success message is not visible");
        softAssert.assertEquals(page.getSuccessMessageText(), expected, "Success message text is not as expected");
    }
}
