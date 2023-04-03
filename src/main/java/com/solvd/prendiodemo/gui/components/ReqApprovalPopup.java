package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ReqApprovalPopup extends BasePopup {

    @FindBy(xpath = "..//input[@value='Submit']")
    private ExtendedWebElement submitButton;

    public ReqApprovalPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickSubmit() {
        LOGGER.info("Clicking submit on requisition approval popup");
        submitButton.click();
    }

    public boolean isClickable() {
        return submitButton.isClickable();
    }
}
