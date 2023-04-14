package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountNumbersTableEntry extends AbstractUIObject {

    @FindBy(xpath = "./td[@id='tdaddress']")
    private ExtendedWebElement shipToAddress;

    @FindBy(xpath = "./td[@id='tdaddress']/following::td[1]")
    private ExtendedWebElement accountNumber;

    public AccountNumbersTableEntry(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getShipToAddress() {
        return shipToAddress.getText();
    }

    public String getAccountNumber() {
        return accountNumber.getText();
    }

    public void waitToBeClickable() {
        waitUntil(ExpectedConditions.elementToBeClickable(shipToAddress.getElement()), EXPLICIT_TIMEOUT);
    }
}
