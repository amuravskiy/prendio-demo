package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPopup extends BasePopup {

    @FindBy(xpath = "//input[@value='OK']")
    private ExtendedWebElement okButton;

    public ConfirmationPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickOK() {
        okButton.click();
    }
}
