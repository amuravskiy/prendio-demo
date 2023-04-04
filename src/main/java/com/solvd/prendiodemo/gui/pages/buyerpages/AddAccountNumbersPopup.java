package com.solvd.prendiodemo.gui.pages.buyerpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.gui.components.BasePopup;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AddAccountNumbersPopup extends BasePopup {

    @FindBy(id = "defbilladdress")
    private ExtendedWebElement selectShipToAddressButton;

    @FindBy(id = "acc_no")
    private ExtendedWebElement accountNumberField;

    @FindBy(id = "SaveAccount")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = "..//div[h2[text()='Default Ship To Address']]")
    private AddressPopup addressPopup;

    public AddAccountNumbersPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public AddressPopup clickSelectShipToAddress() {
        selectShipToAddressButton.click();
        return addressPopup;
    }

    public String fillAccountNumber() {
        String accNum = RandomStringUtils.randomNumeric(4);
        accountNumberField.type(accNum);
        return accNum;
    }

    public void clickSave() {
        saveButton.click();
    }
}