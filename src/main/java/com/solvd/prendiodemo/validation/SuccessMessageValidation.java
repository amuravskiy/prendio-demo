package com.solvd.prendiodemo.validation;

import com.solvd.prendiodemo.web.pages.BasePage;
import org.testng.asserts.SoftAssert;

public class SuccessMessageValidation {

    private final SoftAssert softAssert;
    private BasePage page;

    public SuccessMessageValidation(SoftAssert softAssert, BasePage page) {
        this.softAssert = softAssert;
        this.page = page;
    }

    public void validateSuccessMessageVisible() {
        softAssert.assertTrue(page.isSuccessMessageVisible(), "Success message is not visible");
    }

    public void validateSuccessMessageText(String expected) {
        softAssert.assertEquals(page.getSuccessMessageText(), expected, "Success message is not visible");
    }
}
