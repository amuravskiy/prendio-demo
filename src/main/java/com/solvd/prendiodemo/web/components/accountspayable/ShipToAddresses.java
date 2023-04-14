package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ShipToAddresses extends AbstractUIObject {

    @FindBy(xpath = "./td[2]")
    private ExtendedWebElement line2;

    public ShipToAddresses(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }


    public String getLine2() {
        return line2.getText();
    }

    public void click() {
        getRootExtendedElement().click();
    }
}
