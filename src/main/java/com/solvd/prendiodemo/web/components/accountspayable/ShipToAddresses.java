package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShipToAddresses extends AbstractUIObject {

    @FindBy(xpath = "./td[2]")
    private ExtendedWebElement lineTwo;

    public ShipToAddresses(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getLineTwo() {
        return lineTwo.getText();
    }

    public void waitClickable() {
        waitUntil(ExpectedConditions.elementToBeClickable(this.getRootElement()), EXPLICIT_TIMEOUT);
    }

    public void click() {
        getRootExtendedElement().click();
    }
}
