package com.solvd.prendiodemo.web.components.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.apache.commons.lang3.RandomUtils;
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

    @FindBy(xpath = "..//div[@id='popupform3' and //*[text()='City']]")
    private AddressPopup addressPopup;

    public AddAccountNumbersPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public AddressPopup clickSelectShipToAddress() {
        selectShipToAddressButton.click();
        return addressPopup;
    }

    public String fillAccountNumberRandomly() {
        String accNum = String.valueOf(RandomUtils.nextInt(10, 10_000));
        accountNumberField.type(accNum);
        return accNum;
    }

    public void clickSave() {
        saveButton.click();
    }
}
