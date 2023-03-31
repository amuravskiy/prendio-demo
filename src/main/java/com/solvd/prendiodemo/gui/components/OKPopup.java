package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class OKPopup extends BasePopup {

    @FindBy(xpath = "//input[@value='Ok']")
    private ExtendedWebElement okButton;

    public OKPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickOk() {
        okButton.click();
    }
}
