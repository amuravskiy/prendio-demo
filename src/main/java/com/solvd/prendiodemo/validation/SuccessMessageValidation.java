package com.solvd.prendiodemo.validation;

import com.qaprosoft.carina.core.foundation.webdriver.DriverHelper;
import com.solvd.prendiodemo.web.pages.BasePage;
import org.testng.asserts.SoftAssert;

public class SuccessMessageValidation extends DriverHelper {

    private final SoftAssert softAssert;
    private BasePage page;

    public SuccessMessageValidation(SoftAssert softAssert) {
        this.softAssert = softAssert;
        this.page = new BasePage(getDriver());
    }

    public void validateSuccessMessageVisible() {
        softAssert.assertTrue(page.isSuccessMessageVisible(), "Success message is not visible");
    }

    public void validateSuccessMessageText(String expected) {
        softAssert.assertEquals(page.getSuccessMessageText(), expected, "Success message is not visible");
    }
}
