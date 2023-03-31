package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class YesNoPopup extends BasePopup {

    @FindBy(xpath = "..//input[@value='Yes']")
    protected ExtendedWebElement yesButton;

    public YesNoPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickYes() {
        yesButton.click();
    }
}
